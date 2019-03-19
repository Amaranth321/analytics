package com.kaisquare.vca.programs.kaix1.reportdata;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import com.kaisquare.vca.event.EventManager;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class TrackedObject
{
    private String id;
    private long timeMillis;
    private Float x;
    private Float y;
    private Float w;
    private Float h;
    private String time;    //for backward compatibility

    public void set(String id,
                    long timeMillis,
                    Float x,
                    Float y,
                    Float width,
                    Float height)
    {
        this.id = id;
        this.timeMillis = timeMillis;
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;

        this.time = new DateTime(timeMillis, DateTimeZone.UTC).toString(EventManager.THRIFT_TIME_FORMAT);
    }
}
