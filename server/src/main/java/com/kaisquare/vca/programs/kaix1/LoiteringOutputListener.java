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
class LoiteringOutputListener extends CmdOutputListener
{
    public LoiteringOutputListener(VcaInfo vcaInfo)
    {
        super(vcaInfo);
    }

    @Override
    public void json(Map eventInfoMap)
    {
        if (!eventInfoMap.get("evt").equals("masktrack"))
        {
            return;
        }

        VcaInfo vcaInfo = getVcaInfo();
        logger.info("[{}] {}", vcaInfo, new Gson().toJson(eventInfoMap));

        long time = Util.parseVcaTimeOutput(eventInfoMap.get("time").toString());
        ReportDataEvent.queue(time, vcaInfo.getCamera(), new NoDataReport(vcaInfo.getInstanceId(), EventType.VCA_LOITERING));
    }
}
