package com.kaisquare.vca.db.models;

import com.google.code.morphia.query.Query;
import com.kaisquare.vca.db.MongoModel;
import com.kaisquare.vca.device.DeviceChannelPair;
import com.kaisquare.vca.event.DispatchableEvent;
import com.kaisquare.vca.programs.shared.ReportData;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class ReportDataEvent extends MongoModel implements DispatchableEvent
{
    private long time;
    private DeviceChannelPair camera;
    private ReportData reportData;

    public static void queue(long time,
                             DeviceChannelPair camera,
                             ReportData reportData)
    {
        if (reportData == null)
        {
            throw new NullPointerException();
        }
        ReportDataEvent newEvt = new ReportDataEvent(time, camera, reportData);
        newEvt.save();
    }

    public static ReportDataEvent getOldest()
    {
        Query<ReportDataEvent> query = q(ReportDataEvent.class).order("time");
        return first(query);
    }

    @Override
    public long getEventTime()
    {
        return time;
    }

    @Override
    public DeviceChannelPair getCamera()
    {
        return camera;
    }

    @Override
    public String getEventType()
    {
        return reportData.getEventType();
    }

    @Override
    public String getJsonData()
    {
        return reportData.getJsonData();
    }

    @Override
    public byte[] getBinaryData()
    {
        return reportData.getBinaryData();
    }

    private ReportDataEvent()
    {
        //needed for morphia
    }

    private ReportDataEvent(long time,
                            DeviceChannelPair camera,
                            ReportData reportData)
    {
        this.time = time;
        this.camera = camera;
        this.reportData = reportData;
    }
}
