package com.kaisquare.vca.programs.kaix1;

import com.google.gson.Gson;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.programs.kaix1.reportdata.FaceIndexingReportData;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.utils.Util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
class FaceIndexingOutputListener extends CmdOutputListener
{
    public FaceIndexingOutputListener(VcaInfo vcaInfo)
    {
        super(vcaInfo);
    }

    @Override
    public void json(Map eventInfoMap)
    {
        if (!eventInfoMap.get("evt").equals("rt"))
        {
            return;
        }

        VcaInfo vcaInfo = getVcaInfo();
        logger.info("[{}] {}", vcaInfo, new Gson().toJson(eventInfoMap));

        //send one event for each face
        List<Map<String, String>> outputFaceList = (ArrayList<Map<String, String>>) eventInfoMap.get("tracks");
        String faceFolder = vcaInfo.createFaceFolder();
        for (Map<String, String> faceData : outputFaceList)
        {
            try
            {
                long faceTime = Util.parseVcaTimeOutput(faceData.get("time").toString());
                String faceId = faceData.get("id").toString();
                int duration = Math.round(Float.parseFloat(faceData.get("dur").toString()));

                //read face file
                String snapshotFile = Util.combine(faceFolder, faceData.get("file"));
                if (!new File(snapshotFile).exists())
                {
                    logger.error("Face snapshot not found ({})", snapshotFile);
                    continue;
                }
                byte[] snapshot = Files.readAllBytes(Paths.get(snapshotFile));

                ReportDataEvent.queue(faceTime, vcaInfo.getCamera(), new FaceIndexingReportData(vcaInfo.getInstanceId(), faceId, duration, snapshot));
            }
            catch (Exception e)
            {
                logger.error(e.toString(), e);
            }
        }
    }
}
