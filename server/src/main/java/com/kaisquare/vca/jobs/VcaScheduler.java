package com.kaisquare.vca.jobs;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaManager;
import com.kaisquare.vca.VcaServer;
import com.kaisquare.vca.VcaStatus;
import com.kaisquare.vca.db.models.ErrorEvent;
import com.kaisquare.vca.db.models.VcaInstance;
import com.kaisquare.vca.event.ErrorSource;
import com.kaisquare.vca.event.EventManager;
import com.kaisquare.vca.monitoring.StatusChangeEvent;
import com.kaisquare.vca.process.ProcessStatus;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class VcaScheduler extends PeriodicJob
{
    private static final Logger logger = LogManager.getLogger();
    private final ConcurrentHashMap<String, Long> lastAttemptedTimes = new ConcurrentHashMap<>();

    @Override
    public long getFixedDelay()
    {
        return TimeUnit.SECONDS.toMillis(2);
    }

    @Override
    public void doJob()
    {
        List<VcaInstance> vcaList = VcaInstance.q(VcaInstance.class).asList();
        List<String> instanceIdList = new ArrayList<>();

        //check each schedule
        for (VcaInstance vcaInstance : vcaList)
        {
            VcaInfo vcaInfo = vcaInstance.getVcaInfo();
            instanceIdList.add(vcaInfo.getInstanceId());
            try
            {
                boolean enabled = vcaInfo.isEnabled();
                boolean scheduledNow = vcaInfo.isScheduledNow();
                boolean running = vcaInstance.getVcaStatus().equals(VcaStatus.RUNNING);
                boolean vcaModified = getLastAttempted(vcaInfo.getInstanceId()) <= vcaInstance.getLastModified();

                boolean needToStart = !running && (enabled && scheduledNow);
                boolean needToStop = running && (!enabled || !scheduledNow);
                if (needToStart)
                {
                    attemptStart(vcaInstance, vcaModified);
                }
                if (needToStop)
                {
                    logger.info("instance [{}] need to stop!",vcaInstance.getVcaInfo());
                    VcaManager.getInstance().stop(vcaInstance.getVcaInfo());
                }

                // status can also change even if no action needs to be taken
                // e.g deactivating unscheduled VCA => no action needed
                // but status changed from NOT_SCHEDULED to DISABLED
                if (!needToStart && !needToStop && vcaModified)
                {
                    //check status change
                    VcaStatus newStatus = VcaStatus.calculate(vcaInstance, running ?
                                                                           ProcessStatus.RUNNING :
                                                                           ProcessStatus.EXITED);
                    logger.info("no action status change ({})", newStatus);
                    changeVcaStatus(vcaInstance, newStatus);

                    //mark as attempted
                    lastAttemptedTimes.put(vcaInfo.getInstanceId(), System.currentTimeMillis());
                }
            }
            catch (Exception e)
            {
                logger.error("[{}] {}", vcaInfo, e.getMessage());

                changeVcaStatus(vcaInstance, VcaStatus.ERROR);
                EventManager.getInstance().queue(new ErrorEvent(vcaInfo.getCamera(),
                                                                vcaInfo.getInstanceId(),
                                                                ErrorSource.SERVER,
                                                                e.getMessage()));
            }
        }

        //cleanup removed VCAs from attempt records
        Set<String> attemptList = new LinkedHashSet<>(lastAttemptedTimes.keySet());
        for (String instId : attemptList)
        {
            if (!instanceIdList.contains(instId))
            {
                lastAttemptedTimes.remove(instId);
            }
        }
    }

    private void attemptStart(VcaInstance vcaInstance, boolean vcaModified) throws Exception
    {
        String instanceId = vcaInstance.getVcaInfo().getInstanceId();
        long lastAttempted = getLastAttempted(instanceId);
        long delayBeforeRetry = getDelayBeforeRetry();
        long now = System.currentTimeMillis();

        // don't attempt to start if
        // - instance was not modified and
        // - last attempt delay is still in effect
        if (!vcaModified && (now - lastAttempted < delayBeforeRetry))
        {
            return;
        }

        lastAttemptedTimes.put(instanceId, now);
        VcaManager.getInstance().start(vcaInstance.getVcaInfo());
    }

    private long getLastAttempted(String instanceId)
    {
        long lastAttempted = 0L;
        if (lastAttemptedTimes.containsKey(instanceId))
        {
            lastAttempted = lastAttemptedTimes.get(instanceId);
        }
        return lastAttempted;
    }

    private long getDelayBeforeRetry()
    {
        long now = System.currentTimeMillis();
        long serverStarted = VcaServer.getServerStartedTime();

        //server started recently => higher chance to fail => shorter retry delay
        if (now - serverStarted < TimeUnit.MINUTES.toMillis(10))
        {
            return TimeUnit.SECONDS.toMillis(15);
        }
        else
        {
            return TimeUnit.MINUTES.toMillis(2);
        }
    }

    private void changeVcaStatus(VcaInstance vcaInstance, VcaStatus newStatus)
    {
        vcaInstance.setStatus(newStatus);
        vcaInstance.save();

        //inform platform
        StatusChangeEvent event = new StatusChangeEvent(vcaInstance.getVcaInfo(), newStatus);
        EventManager.getInstance().queue(event);
    }
}
