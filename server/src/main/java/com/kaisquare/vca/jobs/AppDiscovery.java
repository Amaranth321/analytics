package com.kaisquare.vca.jobs;

import com.kaisquare.vca.programs.VcaAppService;

import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class AppDiscovery extends PeriodicJob
{
    private final VcaAppService appService = VcaAppService.getInstance();

    @Override
    public long getFixedDelay()
    {
        return TimeUnit.SECONDS.toMillis(10);
    }

    @Override
    public void doJob()
    {
        appService.updateLoadedApps();
    }
}
