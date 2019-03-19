package com.kaisquare.vca.jobs;

import com.kaisquare.vca.utils.FileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Currently only store the image file for 1 day
 */
public class ImageCleanForDemoJob extends PeriodicJob{

    private Logger logger = LogManager.getLogger(ImageCleanForDemoJob.class);
    private static ConcurrentHashMap<Long,String> imageMap = new ConcurrentHashMap();


    @Override
    public long getFixedDelay() { // every 5min clean the file that expired
        return TimeUnit.SECONDS.toMillis(6);
    }

    @Override
    public void doJob() {
        try {
            logger.info("image clean job start.....");
            for(Long storeTime:imageMap.keySet()){
                long duration = System.currentTimeMillis()-storeTime;
                if(duration>=24*60*60*1000){
                    FileHelper.deleteFileIfExists(imageMap.get(storeTime));
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(),e);
        }
    }


    public static void putImage(String fileName){
        imageMap.put(new Long(System.currentTimeMillis()),fileName);
    }


}
