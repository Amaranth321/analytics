package com.kaisquare.vca.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.util.LinkedHashMap;
import java.util.Map;

public enum NetworkManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final int STARTING_PORT = 20000;

    private static Map<String, Integer> vcaPortMap = new LinkedHashMap<>();

    public static NetworkManager getInstance()
    {
        return INSTANCE;
    }

    public synchronized int assignPort(String instanceId)
    {
        int port = STARTING_PORT;
        while (!isPortAvailable(port))
        {
            port++;
        }

        vcaPortMap.put(instanceId, port);
        return port;
    }

    public synchronized void releaseVcaPort(String instanceId)
    {
        vcaPortMap.remove(instanceId);
    }

    //add by renzongke
    public int getPort(String instanceId){
        return vcaPortMap.get(instanceId);
    }

    private boolean isPortAvailable(int port)
    {
        //check if assigned to other vca
        for (String instId : vcaPortMap.keySet())
        {
            if (vcaPortMap.get(instId).equals(port))
            {
                logger.debug("Port {} is in use by VCA ({})", port, instId);
                return false;
            }
        }

        //check if port can be opened
        try
        {
            ServerSocket s = new ServerSocket(port);
            s.close();
            return true;
        }
        catch (Exception e)
        {
            logger.info("Port {} is not available", port);
            return false;
        }
    }

}
