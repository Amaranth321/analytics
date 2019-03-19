package com.kaisquare.vca.jobs;

import com.kaisquare.vca.streaming.CoreSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class StreamingMonitor extends PeriodicJob
{
    private Logger logger = LogManager.getLogger(StreamingMonitor.class);
    @Override
    public long getFixedDelay()
    {
        int delaySeconds = CoreSessionManager.getExpiryCheckFreqSeconds();
        return TimeUnit.SECONDS.toMillis(delaySeconds);
    }

    @Override
    public void doJob()
    {
        CoreSessionManager.getInstance().keepAliveExpiringSessions();
    }
}
