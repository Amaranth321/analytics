package com.kaisquare.vca.scheduling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;


/**
 * periodsOfDays is the list of periods for each dayOfWeek
 * day integer is the dayOfWeek number (i.e. 1 = Monday, 7 = Sunday)
 *
 * @author Aye Maung
 * @since v4.5
 */
public class RecurrenceRule
{
    private static final Logger logger = LogManager.getLogger();
    private String summary;
    private Map<Integer, List<PeriodOfDay>> periodsOfDays;

    public RecurrenceRule(Map<Integer, List<PeriodOfDay>> periodsOfDays, String summary)
    {
        this.periodsOfDays = periodsOfDays;
        this.summary = summary;
    }

    private RecurrenceRule()
    {
    }

    public String getSummary()
    {
        return summary;
    }

    public boolean isNow()
    {
        try
        {
            DateTime localNow = DateTime.now();

            List<PeriodOfDay> todayPeriods = periodsOfDays.get(localNow.getDayOfWeek());
            if (todayPeriods == null || todayPeriods.size() == 0)
            {
                return false;
            }

            int nowMinutes = localNow.getMinuteOfDay();
            for (PeriodOfDay period : todayPeriods)
            {
                if ((nowMinutes >= period.getStartMinutes()) && (nowMinutes < period.getEndMinutes()))
                {
                    return true;
                }
            }

            return false;
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return false;
        }
    }

}
