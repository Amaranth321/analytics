package com.kaisquare.vca.programs.kaix3;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.programs.shared.ReportData;
import com.kaisquare.vca.shared.ExtractedData;

/**
 * @author Aye Maung
 * @since v4.5
 */
class KaiX3ReportData implements ReportData
{
    private VcaInfo vcaInfo;
    private ExtractedData extractedData;

    private KaiX3ReportData()
    {
        //for morphia
    }

    public KaiX3ReportData(VcaInfo vcaInfo, ExtractedData extractedData)
    {
        this.vcaInfo = vcaInfo;
        this.extractedData = extractedData;
    }

    @Override
    public String getInstanceId()
    {
        return vcaInfo.getInstanceId();
    }

    @Override
    public String getEventType()
    {
        return extractedData.getEventTypeName();
    }

    @Override
    public String getJsonData()
    {
        return extractedData.getJsonData();
    }

    @Override
    public byte[] getBinaryData()
    {
        return extractedData.getBinaryData();
    }
}
