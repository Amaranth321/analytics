package com.kaisquare.vca.process;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaProcess;
import com.kaisquare.vca.jobs.PeriodicJob;
import com.kaisquare.vca.system.Configs;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * DO NOT use this implementation directly. Use {@link com.kaisquare.vca.process.ProcessManager}
 *
 * @author Aye Maung
 * @since v4.5
 */
enum ExecutorSvcProcessManager implements IProcessManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private final ExecutorService vcaExecutor;
    private final ScheduledExecutorService periodicJobExecutor;
    private final ScheduledExecutorService asyncTaskExecutor;

    private final ConcurrentHashMap<String, VcaProcess> vcaProcesses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ScheduledFuture> periodicJobFutures = new ConcurrentHashMap<>();

    public static ExecutorSvcProcessManager getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void startVcaProcess(VcaProcess vcaProcess)
    {
        VcaInfo vcaInfo = vcaProcess.getVcaInfo();
        String vcaInstanceId = vcaInfo.getInstanceId();
        synchronized (vcaProcesses)
        {
            if (vcaProcesses.containsKey(vcaInstanceId))
            {
                stopVcaProcess(vcaInstanceId);
            }

            logger.info("[{}] {}", vcaInfo, StringUtils.join(vcaProcess.getCmdParameters(), " "));
            vcaExecutor.submit(vcaProcess);
            vcaProcesses.put(vcaInstanceId, vcaProcess);
        }
    }

    @Override
    public void stopVcaProcess(String vcaInstanceId)
    {
        if (!vcaProcesses.containsKey(vcaInstanceId))
        {
            return;
        }

        VcaProcess vcaProcess = vcaProcesses.get(vcaInstanceId);
        logger.info("Stopping vca process ({})", vcaProcess.getVcaInfo());
        vcaProcess.destroy();
        vcaProcesses.remove(vcaInstanceId);
    }

    @Override
    public void stopAllVcaProcesses()
    {
        for (String vcaInstanceId : vcaProcesses.keySet())
        {
            stopVcaProcess(vcaInstanceId);
        }
    }

    @Override
    public List<String> getVcaParameters(String vcaInstanceId)
    {
        if (!vcaProcesses.containsKey(vcaInstanceId))
        {
            return null;
        }

        return vcaProcesses.get(vcaInstanceId).getCmdParameters();
    }

    @Override
    public void startPeriodicJob(PeriodicJob periodicJob)
    {
        synchronized (periodicJobFutures)
        {
            String jobName = periodicJob.uniqueName();
            long delay = periodicJob.getFixedDelay();
            logger.info("Starting periodic job ({})", jobName);

            ScheduledFuture future = periodicJobExecutor.scheduleWithFixedDelay(periodicJob,
                                                                                delay,
                                                                                delay,
                                                                                TimeUnit.MILLISECONDS);
            periodicJobFutures.put(jobName, future);
        }
    }

    @Override
    public void stopPeriodicJob(String jobName)
    {
        if (!periodicJobFutures.containsKey(jobName))
        {
            return;
        }

        logger.debug("Stopping periodic job ({})", jobName);
        periodicJobFutures.get(jobName).cancel(false);
        periodicJobFutures.remove(jobName);
    }

    @Override
    public void stopAllPeriodicJobs()
    {
        for (String jobName : periodicJobFutures.keySet())
        {
            stopPeriodicJob(jobName);
        }
    }

    @Override
    public void runAsync(Runnable runnable, long delayMillis)
    {
        asyncTaskExecutor.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void shutdown()
    {
        stopAllVcaProcesses();
        stopAllPeriodicJobs();

        vcaExecutor.shutdown();
        periodicJobExecutor.shutdown();
        asyncTaskExecutor.shutdown();

        logger.info("shutdown completed");
    }

    private ExecutorSvcProcessManager()
    {
        int vcaPoolLimit = Configs.getInstance().getAsInt("vca.concurrent-process-limit", 0);
        int jobPoolSize = Configs.getInstance().getAsInt("vca.periodic-job-thread-pool", 0);
        int asyncPoolSize = 1;

        vcaExecutor = Executors.newFixedThreadPool(vcaPoolLimit, new ProcessThreadFactory("executable"));
        periodicJobExecutor = Executors.newScheduledThreadPool(jobPoolSize, new ProcessThreadFactory("job"));
        asyncTaskExecutor = Executors.newScheduledThreadPool(asyncPoolSize, new ProcessThreadFactory("task"));
        //update shutdown() if a new executor is added.
    }

}
