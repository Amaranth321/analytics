package com.kaisquare.vca.db.models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.query.Query;
import com.kaisquare.vca.db.MongoModel;

/**
 * @author Aye Maung
 * @since v4.5
 */
@Entity
public class SystemInformation extends MongoModel
{
    private static final double FIRST_VER_RELEASE = 4.5;
    private static final double LATEST_RELEASE = 4.5;

    private double releaseNumber;

    public static SystemInformation get()
    {
        Query<SystemInformation> query = q(SystemInformation.class);
        SystemInformation sysInfo = first(query);
        if (sysInfo == null)
        {
            sysInfo = new SystemInformation();
            sysInfo.save();
        }
        return sysInfo;
    }

    public static double getLatestReleaseNumber()
    {
        return LATEST_RELEASE;
    }

    public static double getCurrentReleaseNumber()
    {
        return get().releaseNumber;
    }

    public static void setCurrentReleaseNumber(double releaseNumber)
    {
        SystemInformation sysInfo = get();
        sysInfo.releaseNumber = releaseNumber;
        sysInfo.save();
    }

    private SystemInformation()
    {
        releaseNumber = FIRST_VER_RELEASE; //first release of this server
    }

}
