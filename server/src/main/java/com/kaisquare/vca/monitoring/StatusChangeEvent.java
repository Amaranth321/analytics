package com.kaisquare.vca.monitoring;

import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.device.DeviceChannelPair;
import com.kaisquare.vca.event.DispatchableEvent;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaStatus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class StatusChangeEvent implements DispatchableEvent
{
    private final long time;
    private final VcaInfo vcaInfo;
    private final VcaStatus status;

    public StatusChangeEvent(VcaInfo vcaInfo, VcaStatus status)
    {
        this.time = System.currentTimeMillis();
        this.vcaInfo = vcaInfo;
        this.status = status;
    }

    @Override
    public long getEventTime()
    {
        return time;
    }

    @Override
    public String getEventType()
    {
        return EventType.VCA_STATUS_CHANGED;
    }

    @Override
    public DeviceChannelPair getCamera()
    {
        return vcaInfo.getCamera();
    }

    @Override
    public String getJsonData()
    {
        Map map = new LinkedHashMap();
        map.put("instanceId", vcaInfo.getInstanceId());
        map.put("status", status.toString());
        return new Gson().toJson(map);
    }

    @Override
    public byte[] getBinaryData()
    {
        return new byte[0];
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof StatusChangeEvent)
        {
            StatusChangeEvent other = (StatusChangeEvent) o;
            return this.getInstanceId().equals(other.getInstanceId());
        }
        return false;
    }

    public String getInstanceId()
    {
        return vcaInfo.getInstanceId();
    }
}
