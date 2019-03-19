package com.kaisquare.vca.apps;

import com.kaisquare.vca.exceptions.InvalidOutputException;
import com.kaisquare.vca.exceptions.InvalidSettingsException;
import com.kaisquare.vca.programs.kaix3.KaiX3Implementation;
import com.kaisquare.vca.sdk.Parameter;
import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.shared.ExtractedData;
import com.kaisquare.vca.shared.LocalizableText;
import com.kaisquare.vca.shared.StreamType;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.utils.JsonBuilder;
import com.kaisquare.vca.utils.JsonReader;
import com.kaisquare.vca.utils.SharedUtils;
import com.kaisquare.vca.utils.Util;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Compare to KAI_X1,the execute output data has changed a lot
 *    1) Age:the range of Age value has bean changed  0-[0-3], 1-[4-10], 2-[11-20], 3-[21-30], 4-[31-40], 5-[41-50], 6-[51-60], 7-[61-70], 8-[71,100]
 *    2) Emotion :add a new feature "Angry" 0-Angry,1-Disgust, 2-Fear, 3-Happy, 4-Sad, 5-Surprise, 6-Neutral
 *    3) Race: this feature will not be used currently
 * @author Ren Zong Ke
 * @since v4.7
 */
@PluginImplementation
public class AudienceProfilingApp implements VcaApp{
    private final Logger logger = LogManager.getLogger(AudienceProfilingApp.class);
    private static final String eventTypeName = "event-vca-audienceprofiling";
    private static final String pluginDir = "apps/face_ap/lib";
    private static final String cpuConfigPath = "configs/face_ap_config_lc_cpu.txt";
    private static final String gpuConfigPath = "configs/face_ap_config_lc_gpu.txt";
    private static final boolean runOnGpu = Configs.getInstance().getAsBoolean("vca.run-on-gpu",false);
    private static final String FACE_VANISH_EVT = "face_ap_vanished";
    @Override
    public String appId() {
        return "KAI_X3_face_ap";
    }

    @Override
    public String vcaOutputTypeName() {
        return FACE_VANISH_EVT;
    }

    @Override
    public LocalizableText displayName() {
        return LocalizableText.create("Audience Profiling");
    }

    @Override
    public LocalizableText description() {
        return LocalizableText.create("This app monitors the audience profiling");
    }

    @Override
    public StreamType streamType() {
        return StreamType.RTSP_H264;
    }

    @Override
    public List<Parameter> parameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(getPluginDirParameter());
        parameters.add(getConfigPathParameter());
        return parameters;
    }

    @Override
    public Map<String, String> environmentVariables() {
        return new LinkedHashMap<>();
    }

    @Override
    public List<ExtractedData> extract(Map outputData, ServerInfoProxy serverInfoProxy) throws InvalidOutputException {
        List<ExtractedData> extractedDataList = new ArrayList<ExtractedData>();
        //logger.info("outputData:{}",outputData);
        try {
            JsonReader jsonReader = new JsonReader();
            jsonReader.setThrowRuntimeExceptionOnError(true);
            jsonReader.load(outputData);
            String evtType = jsonReader.getAsString("evt",null);
            logger.info("Audience-Profiling output eventType : {}",evtType);
            //There is 2 types of events . we use vanished events to filter the result we need
            //vanished evt means when a face vanished from camera. there will be a output for this
            if(evtType.equals(FACE_VANISH_EVT)){
                long evtTime = SharedUtils.parseISOTimeStamp(jsonReader.getAsString("time", null));
                List<Map> faces = jsonReader.getAsList("faces",new ArrayList<Map>());
                JsonReader faceReader = new JsonReader();
                faceReader.setThrowRuntimeExceptionOnError(true);
                //add a new boolean field isKaiX3 to identify the way of parse output data on platform side
                for(Map face:faces){
                    faceReader.load(face);
                    float duration = faceReader.getAsFloat("duration",0f);
                    long id = faceReader.getAsLong("id",0l);
                    float age = faceReader.getAsFloat("age",0f);
                    float gender = faceReader.getAsFloat("gender",0f);
                    float emotion = faceReader.getAsFloat("emotion",0f);
                    if(emotion==0f||emotion==1f){
                        emotion =0f;
                    }else if(emotion==3f||emotion==5f){
                        emotion =3f;
                    }else{
                        emotion = 6f;
                    }
                    JsonBuilder jsonBuilder = JsonBuilder.newInstance();
                    String jsonData = jsonBuilder.put("duration",duration)
                            .put("detailId",id)
                            .put("age",age)
                            .put("gender",gender)
                            .put("emotion",emotion)
                            .put("isKaiX3",true).stringify();
                    ExtractedData extractedData = new ExtractedData(evtTime,eventTypeName,jsonData);
                    extractedDataList.add(extractedData);
                }
            }


        } catch (Exception e) {
            throw new InvalidOutputException(e);
        }
        return extractedDataList;
    }

    @Override
    public Runnable preProcessTask() {
        return null;
    }

    @Override
    public Runnable postProcessTask() {
        return null;
    }

    private Parameter getPluginDirParameter(){
        return new Parameter() {
            @Override
            public String arg() {
                return "--plugin_dir";
            }

            @Override
            public boolean valueRequired() {
                return true;
            }

            @Override
            public LocalizableText description() {
                return LocalizableText.create("Directory containing the analytic plugin shared libraries.");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader, ServerInfoProxy serverInfoProxy) throws InvalidSettingsException {
                return Util.combine(KaiX3Implementation.getRuntimeLibraryFolder(),pluginDir);
            }

            @Override
            public boolean optional() {
                return true;
            }

            @Override
            public String defaultValue(ServerInfoProxy serverInfoProxy) {
                return null;
            }
        };
    }

    private Parameter getConfigPathParameter(){
        return new Parameter() {
            @Override
            public String arg() {
                return "--config_path";
            }

            @Override
            public boolean valueRequired() {
                return true;
            }

            @Override
            public LocalizableText description() {
                return LocalizableText.create("Used for verify config file path");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader, ServerInfoProxy serverInfoProxy) throws InvalidSettingsException {
                String configPath = "";
                if(runOnGpu){
                    configPath = Util.combine(KaiX3Implementation.getRuntimeLibraryFolder(),gpuConfigPath);
                }else{
                    configPath = Util.combine(KaiX3Implementation.getRuntimeLibraryFolder(),cpuConfigPath);
                }
                return configPath;
            }

            @Override
            public boolean optional() {
                return true;
            }

            @Override
            public String defaultValue(ServerInfoProxy serverInfoProxy) {
                return null;
            }
        };
    }


}
