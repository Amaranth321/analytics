package com.kaisquare.vca.scheduling;

import com.kaisquare.vca.utils.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

/**
 * @author Aye Maung
 * @since v4.0
 */
public class PeriodOfDay
{
    private static final Logger logger = LogManager.getLogger();
    private int startMinutes;
    private int endMinutes;

    public static Comparator<PeriodOfDay> periodSorter = new Comparator<PeriodOfDay>()
    {
        public int compare(PeriodOfDay a, PeriodOfDay b)
        {
            return a.startMinutes - b.startMinutes;
        }
    };

    public static int dayStart()
    {
        return 0;
    }

    public static int dayEnd()
    {
        return 24 * 60;
    }

    public PeriodOfDay(int startMinutes, int endMinutes)
    {
        this.startMinutes = startMinutes;
        this.endMinutes = endMinutes;
    }

    public int getStartMinutes()
    {
        return startMinutes;
    }

    public int getEndMinutes()
    {
        return endMinutes;
    }

    public boolean isValid()
    {
        if (!TimeUtil.isValid(this))
        {
            logger.error("period ({}) is invalid", this.toString());
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return String.format("%s:%s", startMinutes, endMinutes);
    }

    private PeriodOfDay()
    {
    }
}
