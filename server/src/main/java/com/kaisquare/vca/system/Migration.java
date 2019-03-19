package com.kaisquare.vca.system;

import com.kaisquare.vca.db.models.SystemInformation;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum Migration
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    public static Migration getInstance()
    {
        return INSTANCE;
    }

    public void run()
    {
        logger.debug("Running migrations");
        double currentRelease = SystemInformation.getCurrentReleaseNumber();
        double latestRelease = SystemInformation.getLatestReleaseNumber();
        if (currentRelease == latestRelease)
        {
            return;
        }

        try
        {
            //template
            if (currentRelease < 4.5)
            {
                currentRelease = 4.5;

                //migration codes here

                SystemInformation.setCurrentReleaseNumber(currentRelease);
                logger.info("{} updated system to {}", Util.whichClass(), currentRelease);
            }

            SystemInformation.setCurrentReleaseNumber(latestRelease);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }
}
