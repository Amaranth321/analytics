package com.kaisquare.vca.programs.kaix1.reportdata;

import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.programs.kaix1.KaiX1Implementation;
import com.kaisquare.vca.programs.shared.ReportData;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class TrackReportData implements ReportData
{
    private VcaInfo vcaInfo;
    private List<TrackedObject> trackedObjects;

    public TrackReportData(VcaInfo vcaInfo, List<TrackedObject> trackedObjects)
    {
        this.vcaInfo = vcaInfo;
        this.trackedObjects = trackedObjects;
    }

    @Override
    public String getInstanceId()
    {
        return vcaInfo.getInstanceId();
    }

    @Override
    public String getEventType()
    {
        VcaType vcaType = KaiX1Implementation.getVcaTypeFor(vcaInfo.getAppId());
        switch (vcaType)
        {
            case CROWD_DETECTION:
                return EventType.VCA_CROWD_DETECTION;

            case TRAFFIC_FLOW:
                return EventType.VCA_TRAFFIC_FLOW;

            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getJsonData()
    {
        Gson gson = new Gson();
        Map map = new LinkedHashMap();
        map.put("instanceId", vcaInfo.getInstanceId());
        map.put("tracks", gson.toJson(trackedObjects));
        return gson.toJson(map);
    }

    @Override
    public byte[] getBinaryData()
    {
        return null;
    }

    private TrackReportData()
    {
        //for morphia
    }
}
