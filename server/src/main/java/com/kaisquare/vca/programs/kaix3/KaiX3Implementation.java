package com.kaisquare.vca.programs.kaix3;

import com.google.gson.Gson;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.programs.Program;
import com.kaisquare.vca.programs.VcaAppService;
import com.kaisquare.vca.programs.shared.Implementation;
import com.kaisquare.vca.programs.shared.ProcessBuilderMaker;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.system.Environment;
import com.kaisquare.vca.utils.CmdExecutor;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class KaiX3Implementation implements Implementation{
    private final static Logger logger = LogManager.getLogger(KaiX3Implementation.class);
    private final VcaAppService appService = VcaAppService.getInstance();
    private Map<String, String> appVersionMapFromExe;

    static String getExecutable()
    {
        return Util.combine(getRuntimeLibraryFolder(), "acv");
    }

    public static String getRuntimeLibraryFolder()
    {
        return Util.combine(Program.KAI_X3.getProgramFolder(), Environment.onUnix() ? "linux" : "win");
    }

    public static Map<String, String> globalEnvVariables(){
        Map<String, String> envMap = new LinkedHashMap<>();
        envMap.put("LD_LIBRARY_PATH", Util.combine(getRuntimeLibraryFolder(),"lib"));
        return envMap;
    }

    public static boolean isOsVersionSupported()
    {
        if (Environment.onWindows())
        {
            //todo: it's assumed here that acv apps work on all window versions
            return true;
        }
        return Environment.getUbuntuRelease(false) >= 14.04;
    }


    @Override
    public void runServerStartupSetup()
    {
        if (Environment.onUnix())
        {
            if (!isOsVersionSupported())
            {
                return;
            }
        }

        appService.reloadAll();
        readVersionsFromExecutable(true);
    }

    @Override
    public ProcessBuilderMaker newProcessBuilderMaker(VcaInfo vcaInfo) throws Exception
    {

        Class<? extends VcaApp> appClass = appService.getAppClass(vcaInfo.getAppId());
        VcaApp newAppInstance = appClass.newInstance();

        return KaiX3ProcessBuilderMaker.class
                .getConstructor(VcaApp.class, VcaInfo.class)
                .newInstance(newAppInstance, vcaInfo);
    }

    /**
     * @param refresh false will use the cached map if any, true will execute --info again
     */
    public Map<String, String> readVersionsFromExecutable(boolean refresh)
    {
        if (!isOsVersionSupported())
        {
            return new LinkedHashMap<>();
        }

        if (appVersionMapFromExe != null && !refresh)
        {
            return appVersionMapFromExe;
        }

        //reset
        appVersionMapFromExe = new LinkedHashMap<>();
        String jsonProgramInfo = CmdExecutor.readTillProcessEnds(
                Arrays.asList(getExecutable(), "--info"),
                getRuntimeLibraryFolder(),
                globalEnvVariables(),
                Program.KAI_X3.name(),
                true)
                .get(0);
        //parse
        Map<String, Map<String, Object>> programInfo = new Gson().fromJson(jsonProgramInfo, Map.class);
        for (Map.Entry<String, Map<String, Object>> entry : programInfo.entrySet())
        {
            String appId = entry.getKey();
            String version = String.valueOf(entry.getValue().get("version"));
            appVersionMapFromExe.put(appId, version);
        }

        return appVersionMapFromExe;
    }

    public static String getKaiX3AppId(String appId){
        return appId.replace(String.format("%s_",Program.KAI_X3.name()),"");
    }
}
