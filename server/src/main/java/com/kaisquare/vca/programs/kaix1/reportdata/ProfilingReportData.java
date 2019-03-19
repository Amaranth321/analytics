package com.kaisquare.vca.programs.kaix1.reportdata;

import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.programs.shared.ReportData;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class ProfilingReportData implements ReportData
{
    private String eventType;
    private String instanceId;
    private String id;
    private int duration;
    private Float gender;
    private Float genderavg;
    private Float smile;
    private Float smileavg;
    private Float age;
    private Float ageavg;

    public ProfilingReportData(String eventType,
                               String instanceId,
                               String id,
                               int duration,
                               Float gender,
                               Float genderavg,
                               Float smile,
                               Float smileavg,
                               Float age,
                               Float ageavg)
    {
        this.eventType = eventType;
        this.instanceId = instanceId;
        this.id = id;
        this.duration = duration;
        this.gender = gender;
        this.genderavg = genderavg;
        this.smile = smile;
        this.smileavg = smileavg;
        this.age = age;
        this.ageavg = ageavg;

    }

    @Override
    public String getInstanceId()
    {
        return instanceId;
    }

    @Override
    public String getEventType()
    {
        return eventType;
    }

    @Override
    public String getJsonData()
    {
        return new Gson().toJson(this);
    }

    @Override
    public byte[] getBinaryData()
    {
        return null;
    }

    private ProfilingReportData()
    {
        //for morphia
    }

}
