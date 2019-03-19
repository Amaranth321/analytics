package com.kaisquare.vca.utils;

import com.kaisquare.vca.scheduling.PeriodOfDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
public final class TimeUtil
{
    private TimeUtil()
    {
        //utility class
    }

    public static boolean isValid(int dayOfWeek)
    {
        return dayOfWeek >= 1 && dayOfWeek <= 7;
    }

    /**
     * Note: (startMinutes > endMinutes) is valid to allow periods that cross over midnight.
     * Use crossesMidnight() to check that if needed.
     *
     * @param periodOfDay
     */
    public static boolean isValid(PeriodOfDay periodOfDay)
    {
        if (periodOfDay.getStartMinutes() == periodOfDay.getEndMinutes())
        {
            return false;
        }
        if (periodOfDay.getStartMinutes() < PeriodOfDay.dayStart() ||
            periodOfDay.getEndMinutes() > PeriodOfDay.dayEnd())
        {
            return false;
        }
        return true;
    }

    /**
     * checks if the period covers 12pm midnight
     *
     * @param periodOfDay
     */
    public static boolean crossesMidnight(PeriodOfDay periodOfDay)
    {
        return periodOfDay.getStartMinutes() > periodOfDay.getEndMinutes();
    }

    public static boolean isExpired(long timestampToCheck, long ttl, TimeUnit timeUnit)
    {
        long now = System.currentTimeMillis();
        return now - timestampToCheck > timeUnit.toMillis(ttl);
    }

    public static String format(long mills,String format){
        SimpleDateFormat sdf =  new SimpleDateFormat(format);
        return sdf.format(new Date(mills));
    }
}
