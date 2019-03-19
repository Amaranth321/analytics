package com.kaisquare.vca.programs.kaix2;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.exceptions.InvalidOutputException;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.shared.ExtractedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
class KaiX2OutputListener extends CmdOutputListener
{
    private static final Logger logger = LogManager.getLogger();
    private final VcaApp vcaApp;
    private final ServerInfoProxy serverInfoProxy;

    public KaiX2OutputListener(VcaApp vcaApp, VcaInfo vcaInfo, ServerInfoProxy serverInfoProxy)
    {
        super(vcaInfo);
        this.vcaApp = vcaApp;
        this.serverInfoProxy = serverInfoProxy;
    }

    @Override
    public void json(Map eventInfoMap)
    {
        if (!eventInfoMap.get("evt").equals(vcaApp.appId()))
        {
            return;
        }

        try
        {
            List<ExtractedData> extractedDataList = vcaApp.extract(eventInfoMap, serverInfoProxy);
            if (extractedDataList == null)
            {
                return;
            }

            //send
            for (ExtractedData extractedData : extractedDataList)
            {
                KaiX2ReportData reportData = new KaiX2ReportData(getVcaInfo(), extractedData);
                ReportDataEvent.queue(extractedData.getTime(),
                        getVcaInfo().getCamera(),
                        reportData);
            }
        }
        catch (InvalidOutputException e)
        {
            logger.error(e.toString(), e);
        }
    }
}
