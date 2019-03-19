package com.kaisquare.vca;

import com.google.gson.Gson;
import com.kaisquare.vca.db.models.SystemInformation;
import com.kaisquare.vca.db.models.VcaInstance;
import com.kaisquare.vca.process.ProcessManager;
import com.kaisquare.vca.programs.VcaAppInfo;
import com.kaisquare.vca.programs.VcaAppService;
import com.kaisquare.vca.scheduling.RecurrenceRule;
import com.kaisquare.vca.thrift.*;
import com.kaisquare.vca.utils.SharedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class VcaServicesImpl implements VcaServices.Iface
{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<TVcaInstance> getVcaList()
    {
        List<VcaInstance> vcaInstanceList = VcaInstance.q(VcaInstance.class).asList();
        List<TVcaInstance> returnList = new ArrayList<>();
        for (VcaInstance vcaInstance : vcaInstanceList)
        {
            returnList.add(vcaInstance.toThriftInstance());
        }
        return returnList;
    }

    @Override
    public TVcaInstance addVca(TVcaInfo thriftVcaInfo) throws TException
    {
        VcaInfo vcaInfo = VcaInfo.fromThrift(thriftVcaInfo);
        logger.info("adding vca {}.....",vcaInfo);
        //check existing
        VcaInstance existing = VcaInstance.find(vcaInfo.getInstanceId());
        if (existing != null)
        {
            logger.info("[{}] vca already added. Skipped", vcaInfo);
            return existing.toThriftInstance();
        }

        VcaInstance newInst = VcaInstance.createNew(vcaInfo);
        return newInst.toThriftInstance();
    }

    @Override
    public boolean updateVca(String instanceId, String settings, String recurrenceRule) throws TException
    {
        //check schedule
        RecurrenceRule ruleObj = new Gson().fromJson(recurrenceRule, RecurrenceRule.class);
        if (!SharedUtils.isNullOrEmpty(recurrenceRule) && ruleObj == null)
        {
            throw new TException("Invalid recurrenceRule");
        }

        VcaInstance dbInstance = VcaInstance.find(instanceId);
        if (dbInstance == null)
        {
            throw new TException("Invalid instanceId");
        }

        //vcaInstance
        dbInstance.updateSettings(settings);
        dbInstance.updateRecurrenceRule(ruleObj);
        dbInstance.modified();
        dbInstance.save();

        //stop currently running
        VcaManager.getInstance().stop(dbInstance.getVcaInfo());

        return true;
    }

    @Override
    public boolean removeVca(String instanceId) throws TException
    {
        VcaInstance dbInstance = VcaInstance.find(instanceId);
        if (dbInstance == null)
        {
            return true;
        }

        //remove
        dbInstance.delete();

        //stop currently running
        VcaManager.getInstance().stop(dbInstance.getVcaInfo());

        logger.info("[{}] Removed", dbInstance.getVcaInfo());
        return true;
    }

    @Override
    public boolean activateVca(String instanceId)
    {
        logger.info("activate vca,instanceId:"+instanceId);
        VcaInstance dbInstance = VcaInstance.find(instanceId);
        if (dbInstance == null)
        {
            return true;
        }

        dbInstance.getVcaInfo().setEnabled(true);
        dbInstance.modified();
        dbInstance.save();
        logger.info("activate vca success,instanceId:"+instanceId);
        return true;
    }

    @Override
    public boolean deactivateVca(String instanceId)
    {
        logger.info("deactivate vca,instanceId:"+instanceId);
        VcaInstance dbInstance = VcaInstance.find(instanceId);
        if (dbInstance == null)
        {
            return true;
        }

        dbInstance.getVcaInfo().setEnabled(false);
        dbInstance.modified();
        dbInstance.save();
        logger.info("deactivate vca success,instanceId:"+instanceId);
        return true;
    }

    @Override
    public List<String> getVcaProcessCommands(String instanceId)
    {
        return ProcessManager.getInstance().getVcaParameters(instanceId);
    }

    @Override
    public TVcaServerInfo getServerInformation() throws TException
    {
        logger.debug("==getServerInformation");
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        List<String> vcaThreads = new ArrayList<>();
        for (Thread thread : threadSet)
        {
            String threadName = thread.getName();
            if (threadName.contains("vca"))
            {
                vcaThreads.add(String.format("[%-15s] %s", thread.getState(), threadName));
            }
        }
        Collections.sort(vcaThreads);

        TVcaServerInfo serverInfo = new TVcaServerInfo(VcaServer.getServerStartedTime(),
                                                       SystemInformation.getCurrentReleaseNumber(),
                                                       vcaThreads);
        return serverInfo;
    }

    @Override
    public List<TVcaAppInfo> getSupportedApps() throws TException
    {
        List<VcaAppInfo> infoList = VcaAppService.getInstance().getRegisteredAppsInfo();
        List<TVcaAppInfo> thriftList = new ArrayList<>();
        for (VcaAppInfo appInfo : infoList)
        {
            thriftList.add(new TVcaAppInfo(appInfo.appId,
                                           appInfo.program.name(),
                                           appInfo.version,
                                           appInfo.displayName.map(),
                                           appInfo.description.map()));
        }
        return thriftList;
    }
}
