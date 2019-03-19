package com.kaisquare.vca.system;

import com.kaisquare.vca.utils.CmdExecutor;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Aye Maung
 * @since v4.4
 */
public class Environment
{
    private static final Logger logger = LogManager.getLogger();
    private static final String os;
    private static TimeZone OS_TIME_ZONE = null;
    private static double UBUNTU_RELEASE = 0d;

    static
    {
        if (System.getProperty("os.name").toLowerCase().contains("win"))
        {
            os = "win";
        }
        else
        {
            os = "unix";
        }
    }

    public static boolean onWindows()
    {
        return os.equals("win");
    }

    public static boolean onUnix()
    {
        return os.equals("unix");
    }

    /**
     * @param refresh true to re-read time zone. false to get last-read value
     */
    public static TimeZone getOSTimeZone(boolean refresh)
    {
        if (OS_TIME_ZONE == null || refresh)
        {
            readTimeZoneFromCmdOutput();
        }

        return OS_TIME_ZONE;
    }

    public static double getUbuntuRelease(boolean refresh)
    {
        if (UBUNTU_RELEASE == 0d || refresh)
        {
            readUbuntuReleaseNo();
        }

        return UBUNTU_RELEASE;
    }

    private Environment()
    {
        //utility class
    }

    private static void readTimeZoneFromCmdOutput()
    {
        try
        {
            if (onWindows())
            {
                List<String> cmdParams = new ArrayList<>();
                cmdParams.add("systeminfo");
                List<String> outputs = CmdExecutor.readTillProcessEnds(cmdParams, "TimeZone", false);
                if (outputs.isEmpty())
                {
                    logger.error("{} no cmd output", Util.whichFn());
                    return;
                }

                for (String output : outputs)
                {
                    if (output.contains("Time Zone"))
                    {
                        int start = output.indexOf("UTC") + 3;
                        int end = start + 6;
                        String tzId = "GMT" + output.substring(start, end).replace(":", "");
                        OS_TIME_ZONE = TimeZone.getTimeZone(tzId);
                    }
                }

            }
            else
            {
                List<String> cmdParams = new ArrayList<>();
                cmdParams.add("cat");
                cmdParams.add("/etc/timezone");
                List<String> outputs = CmdExecutor.readTillProcessEnds(cmdParams, "TimeZone", true);
                if (outputs.isEmpty())
                {
                    logger.error("{} no cmd output", Util.whichFn());
                    return;
                }

                OS_TIME_ZONE = TimeZone.getTimeZone(outputs.get(0));
            }
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }

    private static void readUbuntuReleaseNo()
    {
        try
        {
            List<String> cmdParams = Arrays.asList(
                    "sh",
                    "-c",
                    "cat /etc/lsb-release | grep DISTRIB_RELEASE"
            );
            List<String> outputs = CmdExecutor.readTillProcessEnds(cmdParams, "Ubuntu", false);
            UBUNTU_RELEASE = Double.parseDouble(outputs.get(0).split("=")[1]);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }
}
