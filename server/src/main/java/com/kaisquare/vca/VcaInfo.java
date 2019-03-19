package com.kaisquare.vca;

import com.google.gson.Gson;
import com.kaisquare.vca.device.DeviceChannelPair;
import com.kaisquare.vca.scheduling.RecurrenceRule;
import com.kaisquare.vca.system.ResourceManager;
import com.kaisquare.vca.utils.Util;
import com.kaisquare.vca.thrift.TVcaInfo;
import com.kaisquare.vca.utils.SharedUtils;
import org.apache.thrift.TException;

import java.io.File;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class VcaInfo
{
    private String instanceId;
    private String appId;
    private DeviceChannelPair camera;
    private String settings;
    private RecurrenceRule recurrenceRule;
    private boolean enabled;

    public static VcaInfo fromThrift(TVcaInfo tVcaInfo) throws TException
    {
        //device IDs
        if (SharedUtils.isNullOrEmpty(tVcaInfo.getCoreDeviceId()) ||
            SharedUtils.isNullOrEmpty(tVcaInfo.getChannelId()))
        {
            throw new TException("Invalid device IDs");
        }

        //check schedule
        String ruleString = tVcaInfo.getRecurrenceRule();
        RecurrenceRule recurrenceRule = new Gson().fromJson(ruleString, RecurrenceRule.class);
        if (!SharedUtils.isNullOrEmpty(ruleString) && recurrenceRule == null)
        {
            throw new TException("Invalid recurrenceRule");
        }

        return new VcaInfo(tVcaInfo.getInstanceId(),
                           tVcaInfo.getAppId(),
                           new DeviceChannelPair(tVcaInfo.getCoreDeviceId(), tVcaInfo.getChannelId()),
                           tVcaInfo.getSettings(),
                           recurrenceRule,
                           tVcaInfo.isEnabled());
    }

    private VcaInfo(String instanceId,
                    String appId,
                    DeviceChannelPair camera,
                    String settings,
                    RecurrenceRule recurrenceRule,
                    boolean enabled)
    {
        this.instanceId = instanceId;
        this.appId = appId;
        this.camera = camera;
        this.settings = settings;
        this.recurrenceRule = recurrenceRule;
        this.enabled = enabled;
    }

    private VcaInfo()
    {
        //for morphia
    }

    @Override
    public String toString()
    {
        return String.format("%s:%s", instanceId, appId);
    }

    public String getInstanceId()
    {
        return instanceId;
    }

    public String getAppId()
    {
        return appId;
    }

    public DeviceChannelPair getCamera()
    {
        return camera;
    }

    public String getSettings()
    {
        return settings;
    }

    public void setSettings(String settings)
    {
        this.settings = settings;
    }

    public RecurrenceRule getRecurrenceRule()
    {
        return recurrenceRule;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule)
    {
        this.recurrenceRule = recurrenceRule;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isScheduledNow()
    {
        if (recurrenceRule == null)
        {
            return true;
        }

        return recurrenceRule.isNow();
    }

    public String createFaceFolder()
    {
        String customFaceFolder = Util.combine(appId, instanceId);
        String tmpFolder = ResourceManager.getInstance().getTempFolder();
        File folder = new File(Util.combine(tmpFolder, customFaceFolder));
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        return folder.getAbsolutePath();
    }


}
