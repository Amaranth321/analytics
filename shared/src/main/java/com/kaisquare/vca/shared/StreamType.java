package com.kaisquare.vca.shared;

/**
 * @author Aye Maung
 */
public enum StreamType
{
    HTTP_MJPEG("http/mjpeg"),
    HTTP_H264("http/h264"),
    RTSP_H264("rtsp/h264");

    final String coreName;

    StreamType(String coreName)
    {
        this.coreName = coreName;
    }

    public String getCoreName()
    {
        return coreName;
    }
}
