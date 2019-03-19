package com.kaisquare.vca;

import com.google.gson.Gson;
import com.kaisquare.vca.process.IProcessManager;
import com.kaisquare.vca.process.ProcessManager;
import com.kaisquare.vca.process.ProcessStatus;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.programs.shared.ProcessBuilderMaker;
import com.kaisquare.vca.utils.Util;
import com.kaisquare.vca.utils.java.ACResource;
import com.kaisquare.vca.monitoring.StatusListener;
import com.kaisquare.vca.monitoring.VcaStatusListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Aye Maung
 * @since v4.5
 */
public final class VcaProcess implements Runnable
{
    private static final Logger logger = LogManager.getLogger();
    private static Logger exeLogger = LogManager.getLogger("exeLogger");

    private static final IProcessManager processMgr = ProcessManager.getInstance();

    private final ProcessBuilder processBuilder;
    private final Runnable preProcessHook;
    private final Runnable postProcessHook;
    private final CmdOutputListener outputListener;

    private final VcaInfo vcaInfo;
    private final StatusListener statusListener;
    private final List<String> cmdParameters;

    private Process process;

    public VcaProcess(ProcessBuilderMaker processBuilderMaker) throws Exception
    {
        this.processBuilder = processBuilderMaker.make();
        this.preProcessHook = processBuilderMaker.preProcessHook();
        this.postProcessHook = processBuilderMaker.postProcessHook();
        this.outputListener = processBuilderMaker.newOutputListener();

        vcaInfo = processBuilderMaker.getVcaInfo();
        statusListener = new VcaStatusListener(vcaInfo);
        cmdParameters = processBuilder.command();
    }

    @Override
    public void run()
    {
        runHook(preProcessHook);
        statusChanged(ProcessStatus.RUNNING);
        try
        {
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            try (ACResource<Scanner> acScanner = new ACResource<>(new Scanner(process.getInputStream())))
            {
                Scanner scanner = acScanner.get();
                while (scanner.hasNextLine())
                {
                    String line = scanner.nextLine();
                    if (line.isEmpty())
                    {
                        continue;
                    }

                    try
                    {
                        newOutput(line);
                    }
                    catch (Exception e)
                    {
                        logger.error(e.toString(), e);
                    }
                }
            }
        }
        catch (Throwable e)
        {
            logger.error(e.toString(), e);
        }

        statusChanged(ProcessStatus.EXITED);
        runHook(postProcessHook);
    }

    public void destroy()
    {
        try
        {
            if (process != null)
            {
                process.destroy();
            }
        }
        catch (Exception e)
        {
        }
    }

    public VcaInfo getVcaInfo()
    {
        return vcaInfo;
    }

    public List<String> getCmdParameters()
    {
        return cmdParameters;
    }

    private void statusChanged(final ProcessStatus status)
    {
        processMgr.runAsync(new Runnable()
        {
            @Override
            public void run()
            {
                statusListener.changed(status);
            }
        }, 0L);
    }

    private void newOutput(final String output)
    {
        //log to raw exe outputs
        exeLogger.info("[{}] {}", vcaInfo, output);

        //output handler
        processMgr.runAsync(new Runnable()
        {
            @Override
            public void run()
            {
                if (!Util.isJson(output))
                {
                    outputListener.string(output);
                    return;
                }

                Map infoMap = new Gson().fromJson(output, Map.class);
                if (infoMap.containsKey("err_code"))
                {
                    outputListener.error(infoMap);
                }
                else if (infoMap.containsKey("evt"))
                {
                    outputListener.json(infoMap);
                }
                else
                {
                    exeLogger.error("[{}] unknown output : {}", vcaInfo, output);
                }
            }
        }, 0L);
    }

    private void runHook(Runnable hook)
    {
        try
        {
            if (hook != null)
            {
                hook.run();
            }
        }
        catch (Throwable e)
        {
            logger.error(e.toString(), e);
        }
    }


//    public static void main(String[] args){
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(jobPoolSize, new ProcessThreadFactory("job"))
//
//    }
}
