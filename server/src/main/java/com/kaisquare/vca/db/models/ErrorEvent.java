package com.kaisquare.vca.db.models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.db.MongoModel;
import com.kaisquare.vca.device.DeviceChannelPair;
import com.kaisquare.vca.event.DispatchableEvent;
import com.kaisquare.vca.event.ErrorSource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
@Entity
public class ErrorEvent extends MongoModel implements DispatchableEvent
{
    private long time;
    private DeviceChannelPair camera;
    private String instanceId;
    private ErrorSource source;
    private String errorMsg;

    public static ErrorEvent getOldest()
    {
        Query<ErrorEvent> query = q(ErrorEvent.class).order("time");
        return first(query);
    }

    public ErrorEvent(DeviceChannelPair camera,
                      String instanceId,
                      ErrorSource source,
                      String errorMsg)
    {
        this.time = System.currentTimeMillis();
        this.camera = camera;
        this.instanceId = instanceId;
        this.source = source;
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", source, errorMsg);
    }

    @Override
    public DeviceChannelPair getCamera()
    {
        return camera;
    }

    @Override
    public String getEventType()
    {
        return EventType.ERROR;
    }

    @Override
    public long getEventTime()
    {
        return time;
    }

    @Override
    public String getJsonData()
    {
        Map<String, String> eventData = new LinkedHashMap<>();
        eventData.put("instanceId", instanceId);
        eventData.put("source", source.toString());
        eventData.put("error", errorMsg);
        return new Gson().toJson(eventData);
    }

    @Override
    public byte[] getBinaryData()
    {
        return null;
    }

    public String getInstanceId()
    {
        return instanceId;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    private ErrorEvent()
    {
        //needed for morphia
    }
}
