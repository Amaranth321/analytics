package com.kaisquare.vca.process;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum ProcessManager
{
    INSTANCE;

    /**
     * Returns an {@link java.util.concurrent.ExecutorService} based implementation.
     * Implement {@link com.kaisquare.vca.process.IProcessManager} if a different underlying framework is required.
     */
    public static IProcessManager getInstance()
    {
        return ExecutorSvcProcessManager.getInstance();
    }
}
