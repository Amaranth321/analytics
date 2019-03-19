package com.kaisquare.vca.programs.shared;

/**
 * Implement this to queue the event as {@link com.kaisquare.vca.db.models.ReportDataEvent}
 *
 * @author Aye Maung
 * @since v4.5
 */
public interface ReportData
{
    String getInstanceId();

    String getEventType();

    String getJsonData();

    byte[] getBinaryData();
}
