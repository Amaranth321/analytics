package com.kaisquare.vca.streaming;

import com.kaisquare.core.thrift.StreamControlService;
import com.kaisquare.vca.device.DeviceChannelPair;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.utils.ThriftUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Aye Maung
 */
enum StreamThriftClient
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private ThriftUtil.Client<StreamControlService.Iface> streamServiceClient;

    public static StreamThriftClient getInstance()
    {
        return INSTANCE;
    }

    /**
     * Vca server should not start if this fails
     */
    private void initialize() throws Exception
    {
        InetSocketAddress streamServer = Configs.getInstance().getAsInetSocketAddress("core-engine.stream-control-server");
        String host = streamServer.getHostName();
        int port = streamServer.getPort();

        try
        {
            logger.debug("Initializing StreamControlClient ({}:{})", host, port);
            streamServiceClient = ThriftUtil.newServiceClient(
                    StreamControlService.Iface.class,
                    StreamControlService.Client.class,
                    host,
                    port,
                    10000,
                    3,
                    1000);
        }
        catch (Exception e)
        {
            throw new Exception(String.format("Failed to connect the streaming server (%s:%s)", host, port));
        }
    }

    public List<String> beginStreamSession(String sessionKey,
                                           long ttl,
                                           String streamType,
                                           List<String> clientIpAddresses,
                                           DeviceChannelPair camera)
    {
        try
        {
            StreamControlService.Iface serviceIface = getServiceIface();
            return serviceIface.beginStreamSession(sessionKey,
                                                   ttl,
                                                   streamType,
                                                   clientIpAddresses,
                                                   camera.getCoreDeviceId(),
                                                   camera.getChannelId(),
                                                   "",
                                                   ""
            );
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return null;
        }
    }

    public boolean keepStreamSessionAlive(String sessionKey, long ttl, List<String> clientIpAddresses)
    {
        try
        {
            StreamControlService.Iface serviceIface = getServiceIface();
            return serviceIface.keepStreamSessionAlive(sessionKey, ttl, clientIpAddresses);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return false;
        }
    }

    public boolean endStreamSession(String sessionKey)
    {
        try
        {
            StreamControlService.Iface serviceIface = getServiceIface();
            return serviceIface.endStreamSession(sessionKey);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return false;
        }
    }

    private StreamControlService.Iface getServiceIface() throws Exception
    {
        if (streamServiceClient == null)
        {
            initialize();
        }

        return streamServiceClient.getIface();
    }
}
