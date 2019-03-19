package com.kaisquare.vca.jobs;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.AppTempData;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.db.models.VcaInstance;
import com.kaisquare.vca.exceptions.InvalidJsonException;
import com.kaisquare.vca.programs.shared.ReportData;
import com.kaisquare.vca.utils.JsonBuilder;
import com.kaisquare.vca.utils.JsonReader;
import com.kaisquare.vca.utils.SharedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class AppDataCleaner extends PeriodicJob
{
    private static final Logger logger = LogManager.getLogger();
    private static final long FREQ_MILLIS = TimeUnit.SECONDS.toMillis(30);

    @Override
    public long getFixedDelay()
    {
        return FREQ_MILLIS;
    }

    @Override
    public void doJob()
    {
        //expired
        AppTempData.removeExpired();

        //vca with reset hour for 24 hour shops
        checkVcaListWithResetHour();
    }

    private void checkVcaListWithResetHour()
    {
        List<VcaInstance> vcaList = VcaInstance.q(VcaInstance.class).asList();
        long currentCheckTime = System.currentTimeMillis();
        
        for (VcaInstance vcaInstance : vcaList)
        {
            VcaInfo vcaInfo = vcaInstance.getVcaInfo();

            //skip no settings vca
            String jsonSettings = vcaInfo.getSettings();
            if (SharedUtils.isNullOrEmpty(vcaInfo.getSettings()))
            {
                continue;
            }

            try
            {
                JsonReader settingsReader = new JsonReader(jsonSettings);
                if (!settingsReader.containsKey("reset-hour"))
                {
                    continue;
                }

                int resetHourLocal = settingsReader.getAsInt("reset-hour", 0);
                DateTime dtLocal = DateTime.now()
                        .withHourOfDay(resetHourLocal)
                        .withMinuteOfHour(0)
                        .withSecondOfMinute(0);

                long resetTimeMillis = dtLocal.getMillis();

                //remove temp data if reset time has just passed
                long lastCheckedTime = currentCheckTime - FREQ_MILLIS;
                if (lastCheckedTime <= resetTimeMillis && resetTimeMillis < currentCheckTime)
                {
                    AppTempData.remove(vcaInfo.getInstanceId());
                    sendPeopleCountingResetEvent(vcaInfo, currentCheckTime);
                    logger.info("Removed app data for ({}) at resetHour={}:00", vcaInfo, resetHourLocal);
                }
            }
            catch (InvalidJsonException e)
            {
                logger.error(vcaInfo.toString(), e);
            }
        }
    }
    
    private void sendPeopleCountingResetEvent(final VcaInfo vcaInfo, final long currentTimeMillis)
    {
        ReportDataEvent.queue(currentTimeMillis,
                              vcaInfo.getCamera(),
                              new ResetReportData(vcaInfo, "event-vca-people-counting", 
                            		  		JsonBuilder.newInstance()
                                              .put("in", 0)
                                              .put("out", 0)
                                              .put("occupancy", 0)
                                              .stringify(), null)
                              );
    }
    
    /**
     * Static class for reset event used
     */
    static class ResetReportData implements ReportData
    {
    	private VcaInfo vcaInfo;
    	private String eventType;
    	private String jsonData;
    	private byte[] binaryData;
    	
    	private ResetReportData()
		{
    		//for morphia 
		}
    	
    	public ResetReportData(VcaInfo vcaInfo, String eventType, String jsonData, byte[] binaryData)
		{
    		this.vcaInfo = vcaInfo;
    		this.eventType = eventType;
    		this.jsonData = jsonData;
    		this.binaryData = binaryData;
		}
		  
        @Override
        public String getInstanceId()
        {
            return vcaInfo.getInstanceId();
        }

        @Override
        public String getEventType()
        {
            return eventType;
        }

        @Override
        public String getJsonData()
        {
            return jsonData;
        }

        @Override
        public byte[] getBinaryData()
        {
            return binaryData;
        }
    }
}
