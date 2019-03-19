package com.kaisquare.vca.programs.kaix1.reportdata;

import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.programs.shared.ReportData;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class FaceIndexingReportData implements ReportData
{
    private String instanceId;
    private String faceId;
    private int duration;
    private byte[] binaryData;

    public FaceIndexingReportData(String instanceId,
                                  String faceId,
                                  int duration,
                                  byte[] binaryData)
    {
        this.instanceId = instanceId;
        this.faceId = faceId;
        this.duration = duration;
        this.binaryData = binaryData;
    }

    @Override
    public String getInstanceId()
    {
        return instanceId;
    }

    @Override
    public String getEventType()
    {
        return EventType.VCA_FACE_INDEXING;
    }

    @Override
    public String getJsonData()
    {
        Map map = new LinkedHashMap();
        map.put("instanceId", getInstanceId());
        map.put("faceId", faceId);
        map.put("duration", duration);
        return new Gson().toJson(map);
    }

    @Override
    public byte[] getBinaryData()
    {
        return binaryData;
    }

    private FaceIndexingReportData()
    {
        //for morphia
    }

}
