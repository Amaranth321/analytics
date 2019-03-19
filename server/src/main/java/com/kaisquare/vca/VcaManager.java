package com.kaisquare.vca;

import com.kaisquare.vca.db.models.VcaInstance;
import com.kaisquare.vca.process.IProcessManager;
import com.kaisquare.vca.process.ProcessManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum VcaManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private final IProcessManager processMgr = ProcessManager.getInstance();

    public static VcaManager getInstance()
    {
        return INSTANCE;
    }

    public void start(VcaInfo vcaInfo) throws Exception
    {
        VcaProcess vcaProcess = VcaProcessFactory.getInstance().newProcess(vcaInfo);
        processMgr.startVcaProcess(vcaProcess);
    }

    public void stop(VcaInfo vcaInfo)
    {
        processMgr.stopVcaProcess(vcaInfo.getInstanceId());
    }

    public void resetAllVcaStatuses(VcaStatus status)
    {
        logger.debug("Resetting all vca statuses");
        Iterable<VcaInstance> iterable = VcaInstance.q(VcaInstance.class).fetch();
        for (VcaInstance vcaInstance : iterable)
        {
            vcaInstance.setStatus(status);
            vcaInstance.save();
        }
    }
}
