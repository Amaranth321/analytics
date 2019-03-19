package com.kaisquare.vca.programs.kaix1;

import com.google.gson.Gson;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.programs.kaix1.reportdata.NoDataReport;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.utils.Util;
import com.kaisquare.vca.VcaInfo;

import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
class ObjectCountingOutputListener extends CmdOutputListener
{
    private int currentOccupancy;
    private int lastTriggeredOccupancy;

    public ObjectCountingOutputListener(VcaInfo vcaInfo)
    {
        super(vcaInfo);
    }

    @Override
    public void json(Map eventInfoMap)
    {
        if (!eventInfoMap.get("evt").equals("cntio"))
        {
            return;
        }

        VcaInfo vcaInfo = getVcaInfo();

        //parse
        long time = Util.parseVcaTimeOutput(eventInfoMap.get("time").toString());
        int totalIn = Math.round(Float.parseFloat(eventInfoMap.get("r1tor2").toString()));
        int totalOut = Math.round(Float.parseFloat(eventInfoMap.get("r2tor1").toString()));

        int newOccupancy = totalIn - totalOut;
        if (newOccupancy < 0)
        {
            logger.error("Occupancy cannot be negative. Ensure the room is empty before starting VCA.");
            return;
        }

        if (newOccupancy == currentOccupancy)
        {
            return;
        }

        currentOccupancy = newOccupancy;
        logger.info("[{}] new occupancy : {}", vcaInfo, newOccupancy);

        //check
        if (needToTrigger(newOccupancy))
        {
            ReportDataEvent.queue(time, vcaInfo.getCamera(), new NoDataReport(vcaInfo.getInstanceId(), EventType.VCA_OBJECT_COUNTING));

            lastTriggeredOccupancy = newOccupancy;
        }
    }

    public boolean needToTrigger(int occupancy)
    {
        //retrieve settings
        VcaInfo vcaInfo = getVcaInfo();
        String settings = vcaInfo.getSettings();
        Map settingsMap = new Gson().fromJson(settings, Map.class);
        int countLimit = Math.round(Float.parseFloat(String.valueOf(settingsMap.get("countLimit"))));
        boolean continuousTrigger = Boolean.parseBoolean(String.valueOf(settingsMap.get("continuous-trigger")));

        if (occupancy < countLimit)
        {
            lastTriggeredOccupancy = 0;
            return false;
        }

        if (continuousTrigger)
        {
            if (occupancy <= lastTriggeredOccupancy)
            {
                return false;
            }
        }
        else
        {
            if (lastTriggeredOccupancy != 0)
            {
                return false;
            }
        }

        return true;
    }

}
