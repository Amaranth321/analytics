package com.kaisquare.vca.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * All periodic jobs must be extended from this.
 *
 * @author Aye Maung
 * @since v4.5
 */
public abstract class PeriodicJob implements Runnable
{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Fixed delay determines the frequency of the job.
     * e.g. 5 mins delay will restart the job five minutes after the previous round has ended.
     *
     * @return milliseconds
     */
    public abstract long getFixedDelay();

    /**
     * task to be executed
     */
    public abstract void doJob();

    /**
     * Unique name of the job. Usually the class name will suffice for singleton jobs.
     * Otherwise, override this method
     */
    public String uniqueName()
    {
        return this.getClass().getSimpleName();
    }

    @Override
    public void run()
    {
        try
        {
            doJob();
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }
}
