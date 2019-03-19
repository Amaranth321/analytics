package com.kaisquare.vca.utils;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.net.URI;
import java.util.Map;

/**
 * Author:  Aye Maung
 */
public final class Util
{
    private static final Logger logger = LogManager.getLogger();

    public static String whichFn()
    {
        try
        {
            StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
            String callerClass = steArray[2].getClassName();
            String callerMethod = steArray[2].getMethodName();

            String[] splitNames = callerClass.split("\\.");
            callerClass = splitNames[splitNames.length - 1];

            String errorOutput = String.format("In %s.%s() : ", callerClass, callerMethod);
            return errorOutput;
        }
        catch (Exception e)
        {
            return " [unknown] ";
        }
    }

    public static String whichClass()
    {
        try
        {
            StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
            String callerClass = steArray[2].getClassName();

            String[] splitNames = callerClass.split("\\.");
            callerClass = splitNames[splitNames.length - 1];

            String errorOutput = String.format("[%s] ", callerClass);
            return errorOutput;
        }
        catch (Exception e)
        {
            return "[unknown] ";
        }
    }

    public static String callerClass()
    {
        try
        {
            StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
            String callerClass = steArray[3].getClassName();

            String[] splitNames = callerClass.split("\\.");
            callerClass = splitNames[splitNames.length - 1];

            String output = String.format("[%s] ", callerClass);
            return output;
        }
        catch (Exception e)
        {
            return "[unknown] ";
        }
    }

    public static String replaceHost(String originalLink, String newHost)
    {
        try
        {
            URI parsedUrl = new URI(originalLink);
            String currentHost = parsedUrl.getHost();
            return originalLink.replace(currentHost, newHost);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return originalLink;
        }
    }

    public static String cutIfLong(String orig, int lengthLimit)
    {
        return (orig.length() <= lengthLimit ? orig : (orig.substring(0, lengthLimit) + " ..."));
    }

    public static boolean isJson(String jsonString)
    {
        try
        {
            Map map = new Gson().fromJson(jsonString, Map.class);
            return map != null;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static long parseVcaTimeOutput(String dtString)
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss.SSSSSS'Z'").withZoneUTC();
        DateTime dtTime = DateTime.parse(dtString, formatter);
        return dtTime.getMillis();
    }

    public static String combine(String basePath, String relativePath)
    {
        char trailingChar = basePath.charAt(basePath.length() - 1);
        if (trailingChar == '/' || trailingChar == '\\')
        {
            return basePath + relativePath;
        }
        else
        {
            return basePath + "/" + relativePath;
        }
    }
}
