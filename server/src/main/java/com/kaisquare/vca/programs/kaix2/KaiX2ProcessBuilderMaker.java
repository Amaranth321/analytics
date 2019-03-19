package com.kaisquare.vca.programs.kaix2;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.programs.shared.ProcessBuilderMaker;
import com.kaisquare.vca.sdk.Parameter;
import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.streaming.CoreHelper;
import com.kaisquare.vca.system.NetworkManager;
import com.kaisquare.vca.utils.JsonReader;
import com.kaisquare.vca.utils.SharedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
class KaiX2ProcessBuilderMaker implements ProcessBuilderMaker
{
    private static final Logger logger = LogManager.getLogger();

    private final VcaApp vcaApp;
    private final VcaInfo vcaInfo;
    private final ServerInfoProxy serverInfoProxy;

    public KaiX2ProcessBuilderMaker(VcaApp vcaApp, VcaInfo vcaInfo)
    {
        this.vcaApp = vcaApp;
        this.vcaInfo = vcaInfo;
        this.serverInfoProxy = new KaiX2ServerInfoProxy(vcaInfo);
    }

    @Override
    public VcaInfo getVcaInfo()
    {
        return vcaInfo;
    }

    @Override
    public ProcessBuilder make() throws Exception
    {
        List<String> processCmdList = new ArrayList<>();

        //executable
        processCmdList.add(KaiX2Implementation.getExecutable());

        //builder
        processCmdList.addAll(buildCommandList());
        ProcessBuilder pb = new ProcessBuilder(processCmdList);

        //environment variables
        Map<String, String> pbEnvMap = pb.environment();
        pbEnvMap.putAll(KaiX2Implementation.globalEnvVariables());
        pbEnvMap.putAll(vcaApp.environmentVariables());

        return pb;
    }

    @Override
    public CmdOutputListener newOutputListener() throws Exception
    {
        return KaiX2OutputListener.class
                .getConstructor(VcaApp.class, VcaInfo.class, ServerInfoProxy.class)
                .newInstance(vcaApp, vcaInfo, serverInfoProxy);
    }

    @Override
    public Runnable preProcessHook()
    {
        return vcaApp.preProcessTask();
    }

    @Override
    public Runnable postProcessHook()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                //app-side
                Runnable appCleaner = vcaApp.postProcessTask();
                if (appCleaner != null)
                {
                    try
                    {
                        appCleaner.run();
                    }
                    catch (Exception e)
                    {
                        logger.error(e.toString(), e);
                    }
                }

                //server-side
                serverInfoProxy.temporaryFolder().delete();
            }
        };
    }

    private List<String> buildCommandList() throws Exception
    {
        JsonReader configuredSettingsReader = new JsonReader();
        configuredSettingsReader.load(vcaInfo.getSettings());

        Map<String, String> builtParamMap = new LinkedHashMap<>();
        builtParamMap.put("--app", vcaApp.appId());

        //debug port
        int debugPort = NetworkManager.getInstance().assignPort(vcaInfo.getInstanceId());
        builtParamMap.put("--debugport", String.valueOf(debugPort));

        //video
        JsonReader additionParamReader = configuredSettingsReader.getSubReader("additional-params");
        if (additionParamReader.containsKey("i"))
        {
            builtParamMap.put("-i", additionParamReader.getAsString("i", null));
        }
        else
        {
            builtParamMap.put("-i", CoreHelper.getStreamingUrlForVca(vcaInfo, vcaApp.streamType()));
        }

        //vca specific
        List<Parameter> appParams = vcaApp.parameters();
        for (Parameter appParam : appParams)
        {
            String userConfiguredValue = appParam.parse(configuredSettingsReader, serverInfoProxy);

            //not configured
            if (SharedUtils.isNullOrEmpty(userConfiguredValue))
            {
                if (!appParam.optional())
                {
                    String defaultValue = "";
                    if (appParam.valueRequired())
                    {
                        //this will throw an exception if the value must come from the user
                        //hence stopping the vca from running
                        defaultValue = appParam.defaultValue(serverInfoProxy);
                    }

                    builtParamMap.put(appParam.arg(), defaultValue);
                }
            }
            else
            {
                builtParamMap.put(appParam.arg(), userConfiguredValue);
            }
        }

        //override any params specified inside additional parameters
        for (String param : additionParamReader.getFullKeySet())
        {
            //already set above
            if (param.equals("i"))
            {
                continue;
            }

            //not allowed to be overridden
            if (param.equalsIgnoreCase("app") ||
                    param.equalsIgnoreCase("debugport"))
            {
                continue;
            }

            String cmdArg = (param.length() > 1 ? "--" : "-") + param;
            builtParamMap.put(cmdArg, additionParamReader.getAsString(param, null));
        }
        logger.info("SharedUtils.toCommandList(builtParamMap)"+SharedUtils.toCommandList(builtParamMap));
        return SharedUtils.toCommandList(builtParamMap);
    }
}
