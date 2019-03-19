package com.kaisquare.vca;

import com.kaisquare.vca.db.DbManager;
import com.kaisquare.vca.jobs.*;
import com.kaisquare.vca.process.IProcessManager;
import com.kaisquare.vca.process.ProcessManager;
import com.kaisquare.vca.programs.Program;
import com.kaisquare.vca.system.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DO NOT add try-catch in the main. Server should not start if any of the initializations has errors.
 * <p/>
 *
 * @author Aye Maung
 * @since v4.5
 */
public class VcaServer
{
    private static final Logger logger = LogManager.getLogger();
    private static final String SOFTWARE_TITLE = "VCA Server - KAI Square Pte Ltd";
    private static final long serverStartedTime = System.currentTimeMillis();

    public static void main(String[] args)
    {

        CliManager.Arguments cliArguments = CliManager.getInstance().readArguments(args);
        if (cliArguments == null)
        {
            System.exit(1);
        }

        logger.info("---");
        logger.info("VCA server has started ({})", Environment.getOSTimeZone(true).getID());
        if (Environment.onUnix())
        {
            logger.info("Ubuntu Release : {}", Environment.getUbuntuRelease(true));
        }

        //Prepare resource files
        ResourceManager.getInstance().setup();

        //run startup setup for supported VCAs
        Program.runSetup();

        //mongo
        DbManager.getInstance().initMongoDb(cliArguments.mongoUserName, cliArguments.mongoPassword);

        //run migrations
        Migration.getInstance().run();

        //reset all vca
        VcaManager.getInstance().resetAllVcaStatuses(VcaStatus.WAITING);

        //periodic jobs
        startPeriodicJobs();

        /**
         * Start the thrift server the last,
         * otherwise it will start accepting requests before everything is set up
         */
        VcaThriftServer.getInstance().start();

        logger.info("VCA server is ready.");
        logger.info("---");

        //shutdown everything when the server is stopped
        printServerStatuses();
        addShutdownTasks();
    }

    public static String getSoftwareTitle()
    {
        return SOFTWARE_TITLE;
    }

    public static long getServerStartedTime()
    {
        return serverStartedTime;
    }

    private static void startPeriodicJobs()
    {
        IProcessManager processMgr = ProcessManager.getInstance();
        processMgr.startPeriodicJob(new VcaScheduler());
        processMgr.startPeriodicJob(new DataEventsDispatcher());
        processMgr.startPeriodicJob(new ErrorEventsDispatcher());
        processMgr.startPeriodicJob(new StatusEventsDispatcher());
        processMgr.startPeriodicJob(new StreamingMonitor());
        processMgr.startPeriodicJob(new AppDiscovery());
        processMgr.startPeriodicJob(new AppDataCleaner());
    }

    private static void printServerStatuses()
    {
        String format = "%-25s : %s";
        Connection connection = Connection.getInstance();

        logger.info("---");
        logger.info("Platform event server : {}", connection.isEventServerAlive() ? "online" : "OFFLINE");
        logger.info("Core streaming server : {}", connection.isStreamingServerAlive() ? "online" : "OFFLINE");
        logger.info("Vca thrift server     : {}", connection.isThriftServerAlive() ? "online" : "OFFLINE");
    }

    private static void addShutdownTasks()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                ProcessManager.getInstance().shutdown();
            }
        });
    }
}
