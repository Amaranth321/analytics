package com.kaisquare.vca;

import com.kaisquare.vca.db.models.VcaInstance;
import com.kaisquare.vca.process.ProcessStatus;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum VcaStatus
{
    WAITING,
    RUNNING,
    DISABLED,
    NOT_SCHEDULED,
    ERROR;

    public static VcaStatus calculate(VcaInstance currentInstance, ProcessStatus processStatus)
    {
        if (!currentInstance.getVcaInfo().isEnabled())
        {
            return DISABLED;
        }

        switch (processStatus)
        {
            case RUNNING:
                return RUNNING;

            case EXITED:
                if (!currentInstance.getVcaInfo().isScheduledNow())
                {
                    return NOT_SCHEDULED;
                }
                return ERROR;

            default:
                throw new IllegalArgumentException();
        }
    }
}
