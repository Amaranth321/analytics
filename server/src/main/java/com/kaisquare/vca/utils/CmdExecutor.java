package com.kaisquare.vca.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class CmdExecutor
{
    private static final Logger logger = LogManager.getLogger();

    public static List<String> readTillProcessEnds(List<String> commandList,
                                                   String logPrefix,
                                                   boolean printOutputs)
    {
        ProcessBuilder pb = new ProcessBuilder(commandList);
        return read(pb, logPrefix, printOutputs);
    }

    public static List<String> readTillProcessEnds(List<String> commandList,
                                                   Map<String, String> environmentVariables,
                                                   String logPrefix,
                                                   boolean printOutputs)
    {
        ProcessBuilder pb = new ProcessBuilder(commandList);
        pb.environment().putAll(environmentVariables);
        return read(pb, logPrefix, printOutputs);
    }

    public static List<String> readTillProcessEnds(List<String> commandList,
                                                   String directory,
                                                   Map<String, String> environmentVariables,
                                                   String logPrefix,
                                                   boolean printOutputs){
        ProcessBuilder pb = new ProcessBuilder(commandList);
        pb.environment().putAll(environmentVariables);
        File file = new File(directory);
        pb.directory(file);
        return read(pb, logPrefix, printOutputs);
    }

    private static List<String> read(ProcessBuilder processBuilder,
                                     String logPrefix,
                                     boolean printOutputs)
    {
        List<String> outputLines = new ArrayList<>();
        Process process = null;
        Scanner scanner = null;
        try
        {
            if (printOutputs)
            {
                logger.info("[{}] Commands : {}", logPrefix, StringUtils.join(processBuilder.command(), " "));
            }

            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            scanner = new Scanner(process.getInputStream());
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (!line.isEmpty())
                {
                    outputLines.add(line);
                    if (printOutputs)
                    {
                        logger.info("[{}] output : {}", logPrefix, line);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            outputLines = new ArrayList<>();

            if (scanner != null)
            {
                scanner.close();
            }

            if (process != null)
            {
                process.destroy();
            }
        }

        return outputLines;
    }
}
