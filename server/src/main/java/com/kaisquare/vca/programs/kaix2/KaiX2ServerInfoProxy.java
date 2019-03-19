package com.kaisquare.vca.programs.kaix2;

import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.db.models.AppTempData;
import com.kaisquare.vca.programs.Program;
import com.kaisquare.vca.system.Environment;
import com.kaisquare.vca.system.ResourceManager;
import com.kaisquare.vca.utils.Util;
import com.kaisquare.vca.VcaInfo;

import java.io.File;
import java.util.TimeZone;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class KaiX2ServerInfoProxy implements ServerInfoProxy
{
    private final String appId;
    private final String instanceId;

    public KaiX2ServerInfoProxy(VcaInfo vcaInfo)
    {
        this.appId = vcaInfo.getAppId();
        this.instanceId = vcaInfo.getInstanceId();
    }

    @Override
    public TimeZone getOSTimeZone()
    {
        return Environment.getOSTimeZone(false);
    }

    @Override
    public File libraryRootFolder()
    {
        return new File(Program.KAI_X2.getProgramFolder());
    }

    @Override
    public File temporaryFolder()
    {
        String tempRootFolder = ResourceManager.getInstance().getTempFolder();
        String folderName = String.format("%s_%s", appId, instanceId);
        File instFolder = new File(Util.combine(tempRootFolder, folderName));
        if (!instFolder.exists())
        {
            instFolder.mkdirs();
        }
        return instFolder;
    }

    @Override
    public void saveTempData(String jsonData, long ttl)
    {
        AppTempData.saveTempData(instanceId, jsonData, ttl);
    }

    @Override
    public String retrieveTempData()
    {
        AppTempData appData = AppTempData.find(instanceId);
        return appData == null ? null : appData.getJsonData();
    }
}
