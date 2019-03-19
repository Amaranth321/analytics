package com.kaisquare.vca.system;

import com.kaisquare.vca.utils.java.ACResource;
import com.kaisquare.vca.VcaThriftServer;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum Connection
{
    INSTANCE;

    private static final int DEFAULT_CONN_TIMEOUT_SECONDS = 15;

    public static Connection getInstance()
    {
        return INSTANCE;
    }

    public boolean isEventServerAlive()
    {
        InetSocketAddress eventServer = Configs.getInstance().getAsInetSocketAddress("event-server");
        if (eventServer == null)
        {
            return false;
        }
        return isReachable(eventServer, DEFAULT_CONN_TIMEOUT_SECONDS);
    }

    public boolean isStreamingServerAlive()
    {
        InetSocketAddress streamingServer = Configs.getInstance().getAsInetSocketAddress("core-engine.stream-control-server");
        if (streamingServer == null)
        {
            return false;
        }
        return isReachable(streamingServer, DEFAULT_CONN_TIMEOUT_SECONDS);
    }

    public boolean isThriftServerAlive()
    {
        int port = VcaThriftServer.getInstance().getPort();
        InetSocketAddress thriftServer = new InetSocketAddress("127.0.0.1", port);
        return isReachable(thriftServer, DEFAULT_CONN_TIMEOUT_SECONDS);
    }

    private boolean isReachable(InetSocketAddress server, int timeOutSeconds)
    {
        try (ACResource<Socket> acRes = new ACResource<>(new Socket()))
        {
            int timeoutMillis = timeOutSeconds * 1000;
            acRes.get().connect(server, timeoutMillis);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
