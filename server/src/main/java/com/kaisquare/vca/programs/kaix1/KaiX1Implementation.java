package com.kaisquare.vca.programs.kaix1;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaType;
import com.kaisquare.vca.programs.Program;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.programs.shared.Implementation;
import com.kaisquare.vca.programs.shared.ProcessBuilderMaker;
import com.kaisquare.vca.system.Environment;
import com.kaisquare.vca.system.ResourceManager;
import com.kaisquare.vca.utils.CmdExecutor;
import com.kaisquare.vca.utils.Util;
import com.kaisquare.vca.parsers.VcaParserFactory;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Kai VCA first version (Zaw Lin's)
 *
 * @author Aye Maung
 * @since v4.5
 */
public class KaiX1Implementation implements Implementation
{
    private final Map<String, Class<? extends CmdOutputListener>> typeListenerMap = new LinkedHashMap<>();
    private String version;

    public static String getRuntimeLibraryFolder()
    {
        return Util.combine(Program.KAI_X1.getProgramFolder(), Environment.onWindows() ? "win" : "linux");
    }

    public static String getExecutable()
    {
        return Util.combine(getRuntimeLibraryFolder(), "vca");
    }

    /**
     * Kai X1 does not have appId. This method will compose an ID based on VcaType
     */
    public static String getAppIdFor(VcaType vcaType)
    {
        return String.format("%s_%s", Program.KAI_X1.name(), vcaType.getVcaTypeName());
    }

    /**
     * counter part of the method above
     */
    public static VcaType getVcaTypeFor(String appId)
    {
        return VcaType.parse(appId.replace(Program.KAI_X1.name() + "_", ""));
    }

    @Override
    public void runServerStartupSetup()
    {
        //set directories
        String rootDir = Program.KAI_X1.getProgramFolder();
        String resourceDir = getRuntimeLibraryFolder();
        String tempDir = ResourceManager.getInstance().getTempFolder();
        String defaultParamsDir = Util.combine(rootDir, "defaultparams");

        VcaParserFactory parserFactory = VcaParserFactory.getInstance();
        parserFactory.setDefaultFolders(resourceDir, tempDir);
        parserFactory.loadDefaultParameters(defaultParamsDir);

        //read version
        String exeFile = getExecutable();
        List<String> cmdParams = Arrays.asList(exeFile, "-version");
        List<String> cmdOuts = CmdExecutor.readTillProcessEnds(cmdParams, Program.KAI_X1.name(), true);
        version = cmdOuts.get(0);

        //also run version timestamp for debugging
        CmdExecutor.readTillProcessEnds(Arrays.asList(exeFile, "-versiondate"), Program.KAI_X1.name(), true);

        registerTypes();
    }

    @Override
    public ProcessBuilderMaker newProcessBuilderMaker(VcaInfo vcaInfo) throws Exception
    {
        return KaiX1ProcessBuilderMaker.class
                .getConstructor(VcaInfo.class)
                .newInstance(vcaInfo);
    }

    public String getVersion()
    {
        return version;
    }

    public Class<? extends CmdOutputListener> getOutputListenerClass(String appId)
    {
        return typeListenerMap.get(appId);
    }

    private void registerTypes()
    {
        //BI
        typeListenerMap.put(getAppIdFor(VcaType.TRAFFIC_FLOW), TrackOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.PEOPLE_COUNTING), PeopleCountingOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.CROWD_DETECTION), TrackOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.AUDIENCE_PROFILING), ProfilingOutputListener.class);

        //Security
        typeListenerMap.put(getAppIdFor(VcaType.AREA_INTRUSION), IntrusionOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.PERIMETER_DEFENSE), PerimeterDefenseOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.AREA_LOITERING), LoiteringOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.OBJECT_COUNTING), ObjectCountingOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.VIDEO_BLUR), VideoBlurOutputListener.class);
        typeListenerMap.put(getAppIdFor(VcaType.FACE_INDEXING), FaceIndexingOutputListener.class);
    }
}
