package com.kaisquare.vca.programs.shared;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ErrorEvent;
import com.kaisquare.vca.event.ErrorSource;
import com.kaisquare.vca.event.EventManager;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public abstract class CmdOutputListener
{
    // shared by extending classes
    public static final Logger logger = LogManager.getLogger();

    private final VcaInfo vcaInfo;

    protected CmdOutputListener(VcaInfo vcaInfo)
    {
        this.vcaInfo = vcaInfo;
    }

    protected VcaInfo getVcaInfo()
    {
        return vcaInfo;
    }

    public void error(Map errorInfoMap)
    {
        String errorMsg = errorInfoMap.get("msg").toString();
        if (isIgnorable(errorMsg))
        {
            return;
        }

        logger.error("[{}] {}", vcaInfo, errorMsg);
        VcaInfo vcaInfo = getVcaInfo();
        EventManager.getInstance().queue(new ErrorEvent(vcaInfo.getCamera(), vcaInfo.getInstanceId(), ErrorSource.VCA, errorMsg));
    }

    public void string(String output)
    {
       // logger.info(Util.whichFn()+" {} output :: {}",vcaInfo.getAppId(),output);
    }

    public abstract void json(Map eventInfoMap);

    private boolean isIgnorable(String output)
    {
        List<String> ignoreList = Configs.getInstance().getAsList("ignorable-vca-errors", new ArrayList<>());
        for (String ignoreSegment : ignoreList)
        {
            if (output.toLowerCase().contains(ignoreSegment.toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }
}
