package com.kaisquare.vca.sdk;

import java.io.File;
import java.util.TimeZone;

/**
 * Each VCA instance will have their own proxy.
 *
 * @author Aye Maung
 * @since v4.5
 */
public interface ServerInfoProxy
{
    /**
     * server OS's time zone
     */
    TimeZone getOSTimeZone();

    /**
     * This folder will contain library files needed by a specific vca.
     * DO NOT store files in this folder directly. Instead, create sub-folders.
     */
    File libraryRootFolder();

    /**
     * Store temporary files created at runtime in this folder.
     * This folder is a dedicated folder created for each instance of VCA.
     * Hence, it will be auto-deleted when the process has ended.
     */
    File temporaryFolder();

    /**
     * This method is for VCAs that need to restore the last known data
     * when the process died unexpectedly. (e.g. occupancy count)
     *
     * @param jsonData json data to be saved
     * @param ttl      ttl for this data. Server will auto-remove expired data
     */
    void saveTempData(String jsonData, long ttl);

    /**
     * Restores the saved temp data if any.
     */
    String retrieveTempData();
}
