package com.kaisquare.vca.system;

import com.kaisquare.vca.VcaServer;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum CliManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    public static CliManager getInstance()
    {
        return INSTANCE;
    }

    public Arguments readArguments(String[] args)
    {
        CommandLineParser parser = new PosixParser();
        Options options = new Options();

        options.addOption(Option.builder()
                                  .longOpt("help")
                                  .desc("Prints out usage")
                                  .build());

        options.addOption(Option.builder()
                                  .longOpt("mongo_username")
                                  .hasArgs()
                                  .argName("USERNAME")
                                  .desc("[optional] Mongo db username")
                                  .build());

        options.addOption(Option.builder()
                                  .longOpt("mongo_password")
                                  .hasArgs()
                                  .argName("PASSWORD")
                                  .desc("[optional] Mongo db password")
                                  .build());
        try
        {
            CommandLine cmdLine = parser.parse(options, args);
            if (cmdLine.hasOption("help"))
            {
                printHelp(options);
                return null;
            }

            //mongo credentials
            String mongoUsername = cmdLine.getOptionValue("mongo_username", "");
            String mongoPassword = cmdLine.getOptionValue("mongo_password", "");

            return new Arguments(mongoUsername, mongoPassword);
        }
        catch (ParseException e)
        {
            logger.error("Invalid arguments {}", (Object[]) args);
            printHelp(options);
            return null;
        }
    }

    private void printHelp(Options options)
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(120);
        String jarName = "kup-analytics.jar";
        String headerText = "";
        String footerText = VcaServer.getSoftwareTitle();
        formatter.printHelp("java -jar " + jarName, headerText, options, footerText, true);
    }

    public static class Arguments
    {
        public final String mongoUserName;
        public final String mongoPassword;

        public Arguments(String mongoUserName,
                         String mongoPassword)
        {
            this.mongoUserName = mongoUserName;
            this.mongoPassword = mongoPassword;
        }
    }
}
