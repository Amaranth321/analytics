package com.kaisquare.vca.db.models;

import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.kaisquare.vca.db.MongoModel;
import com.kaisquare.vca.scheduling.RecurrenceRule;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaStatus;
import com.kaisquare.vca.thrift.TVcaInfo;
import com.kaisquare.vca.thrift.TVcaInstance;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class VcaInstance extends MongoModel
{
    private VcaInfo vcaInfo;
    private VcaStatus vcaStatus;
    private double releaseNumber;
    private boolean updateRequired;
    private long lastModified;
    private boolean pendingRequest;

    public static VcaInstance createNew(VcaInfo vcaInfo)
    {
        VcaInstance newInst = new VcaInstance();
        newInst.vcaInfo = vcaInfo;
        newInst.vcaStatus = VcaStatus.WAITING;
        newInst.releaseNumber = SystemInformation.getCurrentReleaseNumber();
        newInst.updateRequired = false;
        newInst.save();

        return newInst;
    }

    public static VcaInstance find(String instanceId)
    {
        Query<VcaInstance> query = q(VcaInstance.class).filter("vcaInfo.instanceId", instanceId);
        return first(query);
    }

    private VcaInstance()
    {
        //for morphia
    }

    @Override
    public String toString()
    {
        return getVcaInfo().toString();
    }

    public VcaInfo getVcaInfo()
    {
        return vcaInfo;
    }

    public VcaStatus getVcaStatus()
    {
        return vcaStatus;
    }

    public void setStatus(VcaStatus newStatus)
    {
        vcaStatus = newStatus;
        pendingRequest = false; //status change means some action has been taken for the request
    }

    public double getReleaseNumber()
    {
        return releaseNumber;
    }

    public boolean isUpdateRequired()
    {
        return updateRequired;
    }

    public void setUpdateRequired(boolean updateRequired)
    {
        this.updateRequired = updateRequired;
    }

    public void updateSettings(String settings)
    {
        vcaInfo.setSettings(settings);
    }

    public void updateRecurrenceRule(RecurrenceRule recurrenceRule)
    {
        vcaInfo.setRecurrenceRule(recurrenceRule);
    }

    public long getLastModified()
    {
        return lastModified;
    }

    public void modified()
    {
        lastModified = System.currentTimeMillis();
        pendingRequest = true;
    }

    public TVcaInstance toThriftInstance()
    {
        TVcaInfo tVcaInfo = new TVcaInfo(vcaInfo.getInstanceId(),
                                         vcaInfo.getAppId(),
                                         vcaInfo.getCamera().getCoreDeviceId(),
                                         vcaInfo.getCamera().getChannelId(),
                                         vcaInfo.getSettings(),
                                         new Gson().toJson(vcaInfo.getRecurrenceRule()),
                                         vcaInfo.isEnabled());

        VcaStatus displayStatus = pendingRequest ? VcaStatus.WAITING : vcaStatus;
        return new TVcaInstance(tVcaInfo,
                                releaseNumber,
                                updateRequired,
                                displayStatus.name());
    }

}
