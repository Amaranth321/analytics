package com.kaisquare.vca.system;

import com.kaisquare.vca.utils.JsonReader;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class Configs
{
    private static final Logger logger = LogManager.getLogger();
    private static final JsonReader configReader = new JsonReader();

    static
    {
        try
        {
            configReader.load(FileUtils.readFileToString(new File("conf/config.json"), Charsets.UTF_8));
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }

    public static JsonReader getInstance()
    {
        return configReader;
    }

    private Configs()
    {
    }
}
