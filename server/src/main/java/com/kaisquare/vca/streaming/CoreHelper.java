package com.kaisquare.vca.streaming;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.device.DeviceChannelPair;
import com.kaisquare.vca.shared.StreamType;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.utils.SharedUtils;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CoreHelper
{
    private static final Logger logger = LogManager.getLogger();
    private static final StreamThriftClient streamClient = StreamThriftClient.getInstance();
    private static final CoreSessionManager sessionManager = CoreSessionManager.getInstance();
    private static final List<String> allowedIpList = Arrays.asList("*");  //readonly


    public static String getStreamingUrlForVca(VcaInfo vcaInfo, StreamType streamType) throws Exception
    {
        //find existing session
        StreamingSession activeSession = sessionManager.findSession(vcaInfo.getInstanceId());
        if (activeSession != null)
        {
            return activeSession.getStreamUrl();
        }

        //send request to core
        String sessionKey = CoreSessionManager.generateSessionKey();
        long sessionTtlSeconds = CoreSessionManager.getStreamUrlTtlSeconds();
        String streamUrl = getVideoUrl(sessionKey,
                                       sessionTtlSeconds,
                                       vcaInfo.getCamera(),
                                       streamType);
        if (SharedUtils.isNullOrEmpty(streamUrl))
        {
            throw new Exception("Failed to get stream url from core");
        }

        //save session

        long expiry = System.currentTimeMillis() + (sessionTtlSeconds * 1000);
        sessionManager.saveVcaSession(vcaInfo, sessionKey, streamUrl, expiry);
        return streamUrl;
    }

    public static String getVideoUrl(String sessionKey,
                                     long sessionTtl,
                                     DeviceChannelPair camera,
                                     StreamType streamType)
    {

        List<String> url = streamClient.beginStreamSession(
                sessionKey,
                sessionTtl,
                streamType.getCoreName(),
                allowedIpList,
                camera
        );

        if (url == null || url.size() == 0 || url.get(0).isEmpty())
        {
            return null;
        }

        String streamUrl = url.get(0);

        // use core host defined under config.json
        // for loopback interface usage
        String coreHost = Configs.getInstance().getAsString("core-engine.stream-control-server.host", "");
        if (!coreHost.isEmpty())
        {
            streamUrl = Util.replaceHost(streamUrl, coreHost);
        }

        return streamUrl;
    }

    public static boolean keepSessionAlive(StreamingSession session, long sessionTtl)
    {
        boolean result = streamClient.keepStreamSessionAlive(
                session.getSessionKey(),
                sessionTtl,
                allowedIpList
        );

        if (result)
        {
            long newExpiry = System.currentTimeMillis() + (sessionTtl * 1000);
            session.setExpiry(newExpiry);
        }

        return result;
    }

    public static void endVcaSession(String vcaInstanceId)
    {
        StreamingSession session = sessionManager.findSession(vcaInstanceId);
        if (session != null)
        {
            logger.info(String.format("Removing stream session ({} : key={})", session.getVcaInfo(), session.getSessionKey()));

            streamClient.endStreamSession(session.getSessionKey());
            sessionManager.deleteSession(vcaInstanceId);
        }
    }

    public static void main(String[] args) {
        long a = 1547696850731L;
        Date date = new Date(a);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }
}
