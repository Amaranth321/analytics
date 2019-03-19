package com.kaisquare.vca.system;

import com.kaisquare.vca.utils.FileHelper;
import com.kaisquare.vca.utils.Util;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum ResourceManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String EXT_RESOURCE_FOLDER = "resources/";
    private static final String TEMP_FOLDER = "tmp/";

    private String resAbsolutePath = null;
    private String tmpAbsolutePath = null;

    public static ResourceManager getInstance()
    {
        return INSTANCE;
    }

    private void clearFolders()
    {
        try
        {
            //old folder from 4.5
            FileUtils.deleteDirectory(new File("analytics_resources/"));
            FileUtils.deleteDirectory(new File("analytics_tmp/"));
            FileUtils.deleteDirectory(new File("vca_apps/"));

            FileUtils.deleteDirectory(new File(EXT_RESOURCE_FOLDER));
            FileUtils.deleteDirectory(new File(TEMP_FOLDER));
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }

    public void setup()
    {
        try
        {
            clearFolders();

            File resFolder = FileHelper.getDirectory(EXT_RESOURCE_FOLDER);
            resAbsolutePath = resFolder.getAbsolutePath();
            logger.info("Resource folder    : {}", resAbsolutePath);

            File tmpFolder = FileHelper.getDirectory(TEMP_FOLDER);
            tmpAbsolutePath = tmpFolder.getAbsolutePath();
            logger.info("Temp folder        : {}", tmpAbsolutePath);

            //copy out resources
            FileHelper.copyResourceFolder("vca", resAbsolutePath);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            throw new IllegalStateException(e);
        }
    }

    public String getResourceFolder()
    {
        return resAbsolutePath;
    }

    public String getVcaResourceFolder()
    {
        return Util.combine(resAbsolutePath, "vca");
    }

    public String getTempFolder()
    {
        return tmpAbsolutePath;
    }
}
