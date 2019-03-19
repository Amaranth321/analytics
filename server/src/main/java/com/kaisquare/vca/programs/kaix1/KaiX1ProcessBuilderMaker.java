package com.kaisquare.vca.programs.kaix1;

import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.VcaType;
import com.kaisquare.vca.parsers.AbstractVcaParser;
import com.kaisquare.vca.parsers.VcaParserFactory;
import com.kaisquare.vca.programs.Program;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.programs.shared.ProcessBuilderMaker;
import com.kaisquare.vca.shared.StreamType;
import com.kaisquare.vca.streaming.CoreHelper;
import com.kaisquare.vca.system.NetworkManager;
import com.kaisquare.vca.utils.FileHelper;
import com.kaisquare.vca.utils.JsonReader;
import org.apache.commons.exec.util.StringUtils;
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
class KaiX1ProcessBuilderMaker implements ProcessBuilderMaker
{
    private static final Logger logger = LogManager.getLogger();
    private final KaiX1Implementation implementation;
    private final VcaInfo vcaInfo;
    private final List<String> tempFiles;

    public KaiX1ProcessBuilderMaker(VcaInfo vcaInfo)
    {
        this.implementation = (KaiX1Implementation) Program.KAI_X1.getImplementation();
        this.vcaInfo = vcaInfo;
        tempFiles = new ArrayList<>();
    }

    @Override
    public VcaInfo getVcaInfo()
    {
        return vcaInfo;
    }

    @Override
    public ProcessBuilder make() throws Exception
    {
        //settings reader
        JsonReader settingsReader = new JsonReader();
        settingsReader.load(vcaInfo.getSettings());

        //check custom video link
        JsonReader additionParamReader = settingsReader.getSubReader("additional-params");
        String streamUrl = additionParamReader.containsKey("i") ?
                           additionParamReader.getAsString("i", null) :
                           CoreHelper.getStreamingUrlForVca(vcaInfo, StreamType.HTTP_MJPEG);

        int debugPort = NetworkManager.getInstance().assignPort(vcaInfo.getInstanceId());
        String[] cmdArray = buildCommandArray(KaiX1Implementation.getExecutable(), streamUrl, debugPort);
        return new ProcessBuilder(cmdArray);
    }

    @Override
    public CmdOutputListener newOutputListener() throws Exception
    {
        Class<? extends CmdOutputListener> listenerClass = implementation.getOutputListenerClass(vcaInfo.getAppId());
        return listenerClass
                .getConstructor(VcaInfo.class)
                .newInstance(vcaInfo);
    }

    @Override
    public Runnable preProcessHook()
    {
        return null;
    }

    @Override
    public Runnable postProcessHook()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                for (String tempFile : tempFiles)
                {
                    logger.debug("[{}] Removing temp file ({})", vcaInfo, tempFile);
                    FileHelper.deleteFileIfExists(tempFile);
                }

                NetworkManager.getInstance().releaseVcaPort(vcaInfo.getInstanceId());
            }
        };
    }

    private String[] buildCommandArray(String vcaFile, String streamUrl, int debugPort) throws Exception
    {
        Map<String, String> fullMap = new LinkedHashMap<>();
        fullMap.put("i", streamUrl);
        fullMap.put("httpport", String.valueOf(debugPort));
        fullMap.put("exitonerror", "");

        //parse user settings
        VcaType vcaType = KaiX1Implementation.getVcaTypeFor(vcaInfo.getAppId());
        AbstractVcaParser parser = VcaParserFactory.getInstance().createParser(vcaType.getVcaTypeName(),
                                                                               vcaInfo.getInstanceId());
        fullMap.putAll(parser.getParameterMap(vcaInfo.getSettings()));

        //custom face folder
        String faceFolder = vcaInfo.createFaceFolder();
        fullMap.put("of", faceFolder);

        //temp files
        tempFiles.add(faceFolder);
        tempFiles.addAll(parser.getGeneratedTempFiles());

        //commands
        String cmdString = vcaFile + " " + parser.convertMapToCmdString(fullMap);
        return StringUtils.split(cmdString, " ");
    }
}
