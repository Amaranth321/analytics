package com.kaisquare.vca.jobs;

import com.kaisquare.vca.event.EventManager;

import java.util.concurrent.TimeUnit;

/**
 * Periodically checks event queue and sends events.
 *
 * @author Aye Maung
 * @since v4.5
 */
public class DataEventsDispatcher extends PeriodicJob
{
    @Override
    public long getFixedDelay()
    {
        return TimeUnit.SECONDS.toMillis(1);
    }

    @Override
    public void doJob()
    {
        EventManager.getInstance().processDataEventQueue();
    }
}
