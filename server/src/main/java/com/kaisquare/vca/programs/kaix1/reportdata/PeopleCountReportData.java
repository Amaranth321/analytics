package com.kaisquare.vca.programs.kaix1.reportdata;

import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.programs.shared.ReportData;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class PeopleCountReportData implements ReportData
{
    private String instanceId;
    private int in;
    private int out;
    private int occupancy;

    public PeopleCountReportData(String instanceId,
                                 int in,
                                 int out,
                                 int occupancy)
    {
        this.instanceId = instanceId;
        this.in = in;
        this.out = out;
        this.occupancy = occupancy;
    }

    @Override
    public String getInstanceId()
    {
        return instanceId;
    }

    @Override
    public String getEventType()
    {
        return EventType.VCA_PEOPLE_COUNTING;
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

    private PeopleCountReportData()
    {
        //for morphia
    }
}
