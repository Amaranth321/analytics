package com.kaisquare.vca.programs.kaix1.reportdata;

import com.google.gson.Gson;
import com.kaisquare.vca.programs.shared.ReportData;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * for VCAs without json data. e.g. security VCAs
 *
 * @author Aye Maung
 * @since v4.5
 */
public class NoDataReport implements ReportData
{
    private String instanceId;
    private String eventType;

    public NoDataReport(String instanceId, String eventType)
    {
        this.instanceId = instanceId;
        this.eventType = eventType;
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
        Map map = new LinkedHashMap();
        map.put("instanceId", instanceId);
        return new Gson().toJson(map);
    }

    @Override
    public byte[] getBinaryData()
    {
        return null;
    }

    private NoDataReport()
    {
        //for morphia
    }
}
