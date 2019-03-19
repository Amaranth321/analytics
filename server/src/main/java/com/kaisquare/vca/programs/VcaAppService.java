package com.kaisquare.vca.programs;

import com.kaisquare.vca.VcaType;
import com.kaisquare.vca.programs.kaix1.KaiX1Implementation;
import com.kaisquare.vca.programs.kaix2.KaiX2Implementation;
import com.kaisquare.vca.programs.kaix3.KaiX3Implementation;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.system.ResourceManager;
import com.kaisquare.vca.utils.Util;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum VcaAppService
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private final ConcurrentHashMap<String, VcaApp> registeredApps = new ConcurrentHashMap<>();

    public static VcaAppService getInstance()
    {
        return INSTANCE;
    }

    private Collection<VcaApp> scanJars()
    {
        //check minimum ubuntu requirement
        if (!KaiX2Implementation.isOsVersionSupported())
        {
            return new ArrayList<>();
        }

        Collection<VcaApp> foundApps = new ArrayList<>();
        PluginManager pluginMgr = null;
        PluginManagerUtil pluginMgrUtil = null;
        try
        {
            //app directory
            String vcaResourceDir = ResourceManager.getInstance().getVcaResourceFolder();
            File appDir = new File(Util.combine(vcaResourceDir, "apps"));

            //load plugins
            pluginMgr = PluginManagerFactory.createPluginManager();
            pluginMgr.addPluginsFrom(appDir.toURI());
            pluginMgrUtil = new PluginManagerUtil(pluginMgr);
            foundApps = pluginMgrUtil.getPlugins(VcaApp.class);

            //todo:
            // need to learn how the loading part is done by this library
            // to avoid reference issues
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
        finally
        {
            if (pluginMgrUtil != null)
            {
                pluginMgrUtil.shutdown();
            }
            if (pluginMgr != null)
            {
                pluginMgr.shutdown();
            }
        }
        return foundApps;
    }

    private void register(VcaApp app)
    {
        logger.info("[{}] Registering vca app (AppId: {}, DisplayName: {})",
                    getClass().getSimpleName(),
                    app.appId(),
                    app.displayName().get("en"));

        registeredApps.put(app.appId(), app);
    }

    /**
     * This will clear all currently-registered apps if any. And reload all apps.
     */
    public synchronized void reloadAll()
    {
        registeredApps.clear();

        Collection<VcaApp> apps = scanJars();
        for (VcaApp app : apps)
        {
            if (registeredApps.containsKey(app.appId()))
            {
                logger.error("A duplicate app id found ({}:{}). Skipped", app.appId(), app.displayName());
                continue;
            }

            register(app);
        }

        logger.info("{} app(s) registered", registeredApps.size());
    }

    /**
     * This will scan for newly added jar since the last scan
     */
    public synchronized void updateLoadedApps()
    {
        Collection<VcaApp> scannedApps = scanJars();

        //load new apps
        for (VcaApp scannedApp : scannedApps)
        {
            if (registeredApps.containsKey(scannedApp.appId()))
            {
                continue;
            }
            register(scannedApp);
        }

        //unload apps with missing jars
        List<String> loadedAppIds = new ArrayList<>(registeredApps.keySet());
        for (String loadedAppId : loadedAppIds)
        {
            boolean exists = false;
            for (VcaApp scannedApp : scannedApps)
            {
                if (loadedAppId.equals(scannedApp.appId()))
                {
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                registeredApps.remove(loadedAppId);
                logger.info("no jar package. App unregistered ({})", loadedAppId);
            }
        }
    }

    public boolean appExists(String appId)
    {
        return registeredApps.containsKey(appId);
    }

    public Class<? extends VcaApp> getAppClass(String appId)
    {
        if (!appExists(appId))
        {
            throw new UnsupportedOperationException(appId + " app is either missing or not registered");
        }

        return registeredApps.get(appId).getClass();
    }

    public VcaAppInfo getAppInfo(String appId)
    {
        Program program = getProgram(appId);
        switch (program)
        {
            case KAI_X1:
                String x1Version = ((KaiX1Implementation) Program.KAI_X1.getImplementation()).getVersion();
                VcaType vcaType = KaiX1Implementation.getVcaTypeFor(appId);
                return new VcaAppInfo(vcaType, x1Version);

            case KAI_X2:
                //versions are controlled by the executable
                Map<String, String> x2VersionMap = ((KaiX2Implementation) Program.KAI_X2.getImplementation())
                        .readVersionsFromExecutable(false);
                VcaApp registeredKaix2App = registeredApps.get(appId);
                return new VcaAppInfo(registeredKaix2App, x2VersionMap.get(appId),program);
            //add for KaiX3 program
            case KAI_X3:
                Map<String,String> x3VersionMap = ((KaiX3Implementation)Program.KAI_X3.getImplementation())
                        .readVersionsFromExecutable(false);
                VcaApp registeredKaix3App = registeredApps.get(appId);
                return new VcaAppInfo(registeredKaix3App,x3VersionMap.get(appId),program);
            default:
                throw new IllegalArgumentException(appId);
        }
    }

    public Program getProgram(String appId)
    {
        //hacky, but for supporting old VCAs
        if (appId.contains(Program.KAI_X1.name()))
        {
            return Program.KAI_X1;
        }
        //add by RenZongKe
        if(appId.contains(Program.KAI_X3.name()))
        {
            return Program.KAI_X3;
        }
        if (!appExists(appId))
        {
            throw new UnsupportedOperationException(appId + " does not exist");
        }

        return Program.KAI_X2;
    }

    public List<VcaAppInfo> getRegisteredAppsInfo()
    {
        List<VcaAppInfo> appInfoList = new ArrayList<>();

        //x1 apps
        for (VcaType vcaType : VcaType.values())
        {
            String appId = KaiX1Implementation.getAppIdFor(vcaType);
            appInfoList.add(getAppInfo(appId));
        }

        //x2 apps  and x3 apps
        for (String appId : registeredApps.keySet())
        {
            appInfoList.add(getAppInfo(appId));
        }

        return appInfoList;
    }
}
