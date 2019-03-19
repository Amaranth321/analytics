package com.kaisquare.vca.process;

import com.kaisquare.vca.jobs.PeriodicJob;
import com.kaisquare.vca.VcaProcess;

import java.util.List;

/**
 * @author Aye Maung
 * @since v4.5
 */
public interface IProcessManager
{
    void startVcaProcess(VcaProcess vcaProcess);

    void stopVcaProcess(String vcaInstanceId);

    void stopAllVcaProcesses();

    List<String> getVcaParameters(String vcaInstanceId);

    void startPeriodicJob(PeriodicJob periodicJob);

    void stopPeriodicJob(String jobName);

    void stopAllPeriodicJobs();

    void runAsync(Runnable runnable, long delayMillis);

    void shutdown();
}
