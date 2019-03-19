package com.kaisquare.vca.utils;

import com.kaisquare.vca.VcaServer;
import com.kaisquare.vca.utils.java.ACResource;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class FileHelper
{
    private static final Logger logger = LogManager.getLogger();

    public static List<String> readTextFile(String fileName)
    {
        Path path = Paths.get(fileName);
        try
        {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            logger.error(e.toString(), e);
            return null;
        }
    }

    public static boolean writeTextFile(String line, String fileName)
    {
        try
        {
            FileWriter fstream = new FileWriter(fileName, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(line + ",\n");
            out.close();
            return true;
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return false;
        }
    }

    /**
     * This will create one if the directory does not exist
     *
     * @param folder
     */
    public static File getDirectory(String folder)
    {
        File file = new File(folder);
        if (!file.exists())
        {
            file.mkdirs();
        }

        return file;
    }

    public static void deleteFileIfExists(String filename)
    {
        File fileTemp = new File(filename);
        if (fileTemp.exists())
        {
            FileUtils.deleteQuietly(fileTemp);
        }
    }

    /**
     * This method will copy resources to the destination external directory.
     * <p>
     * The implementation will use different copying codes
     * based on whether application is running inside jar or inside IDE
     *
     * @param resourcePath Resource path relative to 'src/main/resources/'
     * @param destFolder   External directory to copy resources to
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void copyResourceFolder(String resourcePath, String destFolder) throws IOException, URISyntaxException
    {
        final File jarFile = new File(VcaServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if (jarFile.isFile())   // running inside jar
        {
            logger.info("Copying out jar resource files");
            try (ACResource<JarFile> acJar = new ACResource<>(new JarFile(jarFile)))
            {
                final JarFile jar = acJar.get();
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements())
                {
                    final String resourceName = entries.nextElement().getName();
                    if (!resourceName.startsWith(resourcePath) || resourceName.endsWith("/"))
                    {
                        continue;
                    }

                    logger.debug("copying {}", resourceName);
                    try (ACResource<InputStream> acIs = new ACResource<>(VcaServer.class.getResourceAsStream("/" + resourceName)))
                    {
                        File outputFile = new File(Util.combine(destFolder, resourceName));
                        FileUtils.copyInputStreamToFile(acIs.get(), outputFile);

                        //set permissions
                        outputFile.setExecutable(true, false);
                        outputFile.setReadable(true, false);
                    }
                }
            }
        }
        else // running inside IDE
        {
            logger.info("Copying project resources for dev");
            final URL url = VcaServer.class.getResource("/" + resourcePath);
            if (url == null)
            {
                logger.error("Resource folder not found ({})", resourcePath);
            }

            final File srcFolder = new File(url.toURI());
            FileUtils.copyDirectory(srcFolder, new File(Util.combine(destFolder, resourcePath)));
        }
    }


}
