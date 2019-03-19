package com.kaisquare.vca.monitoring;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaStatus;
import com.kaisquare.vca.db.models.VcaInstance;
import com.kaisquare.vca.event.EventManager;
import com.kaisquare.vca.process.ProcessStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class VcaStatusListener implements StatusListener
{
    private static final Logger logger = LogManager.getLogger();
    private final VcaInfo vcaInfo;

    public VcaStatusListener(VcaInfo vcaInfo)
    {
        this.vcaInfo = vcaInfo;
    }

    @Override
    public void changed(ProcessStatus processStatus)
    {
        VcaInstance vcaInstance = VcaInstance.find(vcaInfo.getInstanceId());
        VcaStatus newStatus = VcaStatus.calculate(vcaInstance, processStatus);
        vcaInstance.setStatus(newStatus);
        vcaInstance.save();
        logger.info("[{}] status changed : {}", vcaInfo, newStatus);

        //send event
        StatusChangeEvent event = new StatusChangeEvent(vcaInfo, newStatus);
        EventManager.getInstance().queue(event);
    }
}