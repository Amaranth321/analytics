package com.kaisquare.vca.shared;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class ExtractedData
{
    private long time;
    private String eventTypeName;
    private String jsonData;
    private byte[] binaryData;

    private boolean downloadRequire;
    private ExtractedData()
    {
        //for morphia
    }

    public ExtractedData(long time, String eventTypeName, String jsonData)
    {
        this(time, eventTypeName, jsonData, null,false);
    }

    public ExtractedData(long time, String eventTypeName, String jsonData,boolean downloadRequire)
    {
        this(time, eventTypeName, jsonData, null,downloadRequire);
    }

    public ExtractedData(long time, String eventTypeName, String jsonData, byte[] binaryData,boolean downloadRequire)
    {
        this.time = time;
        this.eventTypeName = eventTypeName;
        this.jsonData = jsonData;
        this.binaryData = binaryData;
        this.downloadRequire = downloadRequire;
    }

    public long getTime()
    {
        return time;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public String getJsonData()
    {
        return jsonData;
    }

    public byte[] getBinaryData()
    {
        return binaryData;
    }

    public boolean isDownloadRequire() {
        return downloadRequire;
    }
}
