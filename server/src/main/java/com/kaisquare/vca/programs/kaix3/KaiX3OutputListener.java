package com.kaisquare.vca.programs.kaix3;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.exceptions.InvalidOutputException;
import com.kaisquare.vca.jobs.ImageCleanForDemoJob;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.shared.ExtractedData;
import com.kaisquare.vca.system.NetworkManager;
import com.kaisquare.vca.utils.HttpUtil;
import com.kaisquare.vca.utils.JsonBuilder;
import com.kaisquare.vca.utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

public class KaiX3OutputListener extends CmdOutputListener{
    private static final Logger logger = LogManager.getLogger();
    private final VcaApp vcaApp;
    private final ServerInfoProxy serverInfoProxy;

    public KaiX3OutputListener(VcaApp vcaApp, VcaInfo vcaInfo, ServerInfoProxy serverInfoProxy)
    {
        super(vcaInfo);
        this.vcaApp = vcaApp;
        this.serverInfoProxy = serverInfoProxy;
    }

    @Override
    public void json(Map eventInfoMap) {
        if (!eventInfoMap.get("evt").equals(vcaApp.appId()) &&
                !eventInfoMap.get("evt").equals(vcaApp.vcaOutputTypeName()))
        {
            return;
        }

        try
        {
            List<ExtractedData> extractedDataList = vcaApp.extract(eventInfoMap, serverInfoProxy);
            //ignore unnecessary vca output
            if (extractedDataList == null || extractedDataList.isEmpty())
            {
                return;
            }
            JsonReader jsonReader = new JsonReader();
            jsonReader.setThrowRuntimeExceptionOnError(true);
            ExtractedData data = extractedDataList.get(0);
            jsonReader.load(data.getJsonData());
            if(data.isDownloadRequire()){//if need download debugger image(currently debugger view url extension
                                        // is detections.jpg)
                String fileName = jsonReader.getAsString("fileName",null);
                String host = Inet4Address.getLocalHost().getHostAddress();
                int port = NetworkManager.getInstance().getPort(getVcaInfo().getInstanceId());
                boolean result = HttpUtil.download(String.format("http://%s:%s/detections.jpg",host,port),fileName);
                if(result){//put into map which maintained by fileCleanJob
                    ImageCleanForDemoJob.putImage(fileName);
                }
            }
            for (ExtractedData extractedData : extractedDataList)
            {
                KaiX3ReportData reportData = new KaiX3ReportData(getVcaInfo(), extractedData);
                ReportDataEvent.queue(extractedData.getTime(),
                        getVcaInfo().getCamera(),
                        reportData);

            }
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }
}
