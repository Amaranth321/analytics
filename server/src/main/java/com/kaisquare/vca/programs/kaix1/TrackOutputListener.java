package com.kaisquare.vca.programs.kaix1;

import com.google.gson.Gson;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.programs.kaix1.reportdata.TrackReportData;
import com.kaisquare.vca.programs.kaix1.reportdata.TrackedObject;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Output listener for Crowd density and Traffic flow
 * If one of them needs a different treatment, create two separate listeners instead of modifying this.
 *
 * @author Aye Maung
 * @since v4.5
 */
class TrackOutputListener extends CmdOutputListener
{
    private static final int ACCUMULATE_MINS = 1;

    private List<TrackedObject> accumulatedTracks = new ArrayList<>();
    private long lastSentTime;

    public TrackOutputListener(VcaInfo vcaInfo)
    {
        super(vcaInfo);
    }

    @Override
    public void json(Map eventInfoMap)
    {
        if (!eventInfoMap.get("evt").equals("track"))
        {
            return;
        }

        VcaInfo vcaInfo = getVcaInfo();

        //get filter settings
        Map settings = new Gson().fromJson(vcaInfo.getSettings(), Map.class);
        Float minWidth = Float.parseFloat(String.valueOf(settings.get("minWidth")));
        Float minHeight = Float.parseFloat(String.valueOf(settings.get("minHeight")));

        //compile tracks
        List<Map> trackObjectList = (List<Map>) eventInfoMap.get("tracks");
        for (Map trackData : trackObjectList)
        {
            accumulate(trackData, minWidth, minHeight);
        }

        //check last sent time
        long now = System.currentTimeMillis();
        if (now - lastSentTime < TimeUnit.MINUTES.toMillis(ACCUMULATE_MINS))
        {
            return;
        }

        //queue and update cache
        synchronized (this)
        {
            if (!accumulatedTracks.isEmpty())
            {
                ReportDataEvent.queue(now, vcaInfo.getCamera(), new TrackReportData(vcaInfo, accumulatedTracks));
            }
            lastSentTime = now;
            accumulatedTracks.clear();
        }
    }

    private void accumulate(Map trackData, Float minWidth, Float minHeight)
    {
        if (trackData == null)
        {
            return;
        }

        long trackTime = Util.parseVcaTimeOutput(String.valueOf(trackData.get("time")));
        String id = String.valueOf(trackData.get("id"));
        Float x = Float.parseFloat(String.valueOf(trackData.get("x")));
        Float y = Float.parseFloat(String.valueOf(trackData.get("y")));
        Float w = Float.parseFloat(String.valueOf(trackData.get("w")));
        Float h = Float.parseFloat(String.valueOf(trackData.get("h")));

        //Ignore objects at (0,0)
        if (x == 0 || y == 0)
        {
            return;
        }

        //Ignore small objects
        if (w < minWidth || h < minHeight)
        {
            return;
        }

        TrackedObject trackedObject = new TrackedObject();
        trackedObject.set(id, trackTime, x, y, w, h);

        synchronized (this)
        {
            accumulatedTracks.add(trackedObject);
        }
    }

}
