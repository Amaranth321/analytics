package com.kaisquare.vca.process;

import java.util.concurrent.ThreadFactory;

/**
 * @author Aye Maung
 * @since v4.5
 */
class ProcessThreadFactory implements ThreadFactory
{
    private final String threadPostfix;

    public ProcessThreadFactory(String threadPostfix)
    {
        this.threadPostfix = threadPostfix;
    }

    @Override
    public Thread newThread(Runnable r)
    {
        String threadName = String.format("vca_%s", threadPostfix);
        return new Thread(r, threadName);
    }

}
