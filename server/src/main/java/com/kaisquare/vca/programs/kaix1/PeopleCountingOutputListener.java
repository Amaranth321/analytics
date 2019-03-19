package com.kaisquare.vca.programs.kaix1;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.programs.kaix1.reportdata.PeopleCountReportData;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.utils.JsonBuilder;
import com.kaisquare.vca.utils.JsonReader;
import com.kaisquare.vca.utils.SharedUtils;
import com.kaisquare.vca.utils.Util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
class PeopleCountingOutputListener extends CmdOutputListener
{
    private static final long DATA_CACHE_TTL = TimeUnit.HOURS.toMillis(2);
    private int prevTotalIn = 0;
    private int prevTotalOut = 0;

    public PeopleCountingOutputListener(VcaInfo vcaInfo)
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
        int newTotalIn = Math.round(Float.parseFloat(eventInfoMap.get("r1tor2").toString()));
        int newTotalOut = Math.round(Float.parseFloat(eventInfoMap.get("r2tor1").toString()));

        //update net counts
        int netIn = newTotalIn - prevTotalIn;
        int netOut = newTotalOut - prevTotalOut;

        //No changes
        if (netIn == 0 && netOut == 0)
        {
            return;
        }

        //Reset if negative nets
        if (netIn < 0 || netOut < 0)
        {
            prevTotalIn = 0;
            prevTotalOut = 0;
            return;
        }

        //check previous occupancy
        int previousOccupancy = 0;
        KaiX1ServerInfoProxy serverInfoProxy = new KaiX1ServerInfoProxy(vcaInfo);
        String jsonCache = serverInfoProxy.retrieveTempData();
        if (!SharedUtils.isNullOrEmpty(jsonCache))
        {
            try
            {
                JsonReader cacheReader = new JsonReader();
                cacheReader.load(jsonCache);
                previousOccupancy = cacheReader.getAsInt("occupancy", 0);
            }
            catch (Exception e)
            {
                return;
            }
        }

        //save new occupancy
        int newOccupancy = previousOccupancy + (netIn - netOut);
        String updatedCache = JsonBuilder.newInstance().put("occupancy", newOccupancy).stringify();
        serverInfoProxy.saveTempData(updatedCache, DATA_CACHE_TTL);

        logger.info("[{}] in:{}, out:{}, occupancy:{}", vcaInfo, netIn, netOut, newOccupancy);

        //send
        PeopleCountReportData reportData = new PeopleCountReportData(vcaInfo.getInstanceId(), netIn, netOut, newOccupancy);
        ReportDataEvent.queue(time, vcaInfo.getCamera(), reportData);

        //update
        prevTotalIn = newTotalIn;
        prevTotalOut = newTotalOut;
    }
}
