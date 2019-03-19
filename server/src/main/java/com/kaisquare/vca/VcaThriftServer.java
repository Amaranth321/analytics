package com.kaisquare.vca;

import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.thrift.VcaServices;
import com.kaisquare.vca.utils.ThriftUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum VcaThriftServer
{
    INSTANCE;

    private TServer thriftServer;

    private static final Logger logger = LogManager.getLogger();
    public static VcaThriftServer getInstance()
    {
        return INSTANCE;
    }

    public int getPort()
    {
        return Configs.getInstance().getAsInt("vca.thrift-server-port", 0);
    }

    public void start()
    {
        try
        {
            int port = getPort();
            logger.info("Starting thrift server ({})", port);

            VcaServicesImpl handler = new VcaServicesImpl();
            VcaServices.Processor processor = new VcaServices.Processor(handler);
            thriftServer = ThriftUtil.newServiceServer(processor, port);
            logger.info("thrift server ({}) has started successfully", port);
        }
        catch (TTransportException e)
        {
            logger.error(e.toString(), e);
            throw new IllegalStateException(e);
        }
    }

    public void stop()
    {
        thriftServer.stop();
    }
}
