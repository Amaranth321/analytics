package com.kaisquare.vca.streaming;


import org.apache.commons.lang3.RandomStringUtils;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.VcaInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum CoreSessionManager
{
    INSTANCE;

    private static Logger logger = LogManager.getLogger(CoreSessionManager.class);

    private static ConcurrentHashMap<String, StreamingSession> vcaSessionMap = new ConcurrentHashMap<>();

    public static CoreSessionManager getInstance()
    {
        return INSTANCE;
    }

    public static String generateSessionKey()
    {
        return RandomStringUtils.randomAlphanumeric(20).toLowerCase();
    }

    public static int getExpiryCheckFreqSeconds()
    {
        return Configs.getInstance().getAsInt("streaming.expiry-check-freq-secs", 5);
    }

    public static int getStreamUrlTtlSeconds()
    {
        return Configs.getInstance().getAsInt("streaming.url-ttl-secs", 0);
    }

    public void saveVcaSession(VcaInfo vcaInfo, String sessionKey, String url, long expiry)
    {
        StreamingSession session = new StreamingSession(vcaInfo, sessionKey, url, expiry);
        vcaSessionMap.put(vcaInfo.getInstanceId(), session);
    }

    public StreamingSession findSession(String vcaInstanceId)
    {
        return vcaSessionMap.get(vcaInstanceId);
    }

    public void deleteSession(String vcaInstanceId)
    {
        if (vcaSessionMap.containsKey(vcaInstanceId))
        {
            vcaSessionMap.remove(vcaInstanceId);
        }
    }

    public void keepAliveExpiringSessions()
    {
        long ttlSeconds = getStreamUrlTtlSeconds();

        for (Map.Entry<String,StreamingSession> entry: vcaSessionMap.entrySet())
        {
            String instanceId = entry.getKey();
            StreamingSession session = vcaSessionMap.get(instanceId);
            long timeLeft = session.getExpiry() - System.currentTimeMillis();

            //ensure stream doesn't die before the next check cycle
            if (timeLeft < (2 * getExpiryCheckFreqSeconds() * 1000))
            {
                boolean result = CoreHelper.keepSessionAlive(session, ttlSeconds);
                if (!result)
                {
                    vcaSessionMap.remove(instanceId);
                }
            }
        }


//            for (String instanceId : vcaSessionMap.keySet())
//            {
//                logger.info("instanceId:{}",instanceId);
//                StreamingSession session = vcaSessionMap.get(instanceId);
//                logger.info("keep session alive:::::: {}",session);
//                long timeLeft = session.getExpiry() - System.currentTimeMillis();
//
//                //ensure stream doesn't die before the next check cycle
//                if (timeLeft < (2 * getExpiryCheckFreqSeconds() * 1000))
//                {
//                    logger.info("session {} after check should keep alive...",session);
//                    boolean result = CoreHelper.keepSessionAlive(session, ttlSeconds);
//                    if (!result)
//                    {
//                        logger.error("keep session alive failed for session :{}",session);
//                        vcaSessionMap.remove(instanceId);
//                    }
//                }
//            }
    }
}
