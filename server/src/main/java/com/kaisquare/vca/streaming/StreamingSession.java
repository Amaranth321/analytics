package com.kaisquare.vca.streaming;

import com.kaisquare.vca.VcaInfo;

/**
 * This session is created when core engine successfully returns a url
 *
 * @author Aye Maung
 */
class StreamingSession
{
    private final VcaInfo vcaInfo;
    private final String sessionKey;
    private final String streamUrl;
    private long expiry;

    public StreamingSession(VcaInfo vcaInfo,
                            String sessionKey,
                            String streamUrl,
                            long expiry)
    {
        this.vcaInfo = vcaInfo;
        this.sessionKey = sessionKey;
        this.streamUrl = streamUrl;
        this.expiry = expiry;
    }

    public VcaInfo getVcaInfo()
    {
        return vcaInfo;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public String getStreamUrl()
    {
        return streamUrl;
    }

    public long getExpiry()
    {
        return expiry;
    }

    public void setExpiry(long expiry)
    {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "StreamingSession{" +
                "vcaInfo=" + vcaInfo +
                ", sessionKey='" + sessionKey + '\'' +
                ", streamUrl='" + streamUrl + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}
