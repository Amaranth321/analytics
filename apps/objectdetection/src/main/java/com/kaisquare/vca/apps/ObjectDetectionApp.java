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
import com.kaisquare.vca.system.ResourceManager;
import com.kaisquare.vca.utils.*;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@PluginImplementation
public class ObjectDetectionApp implements VcaApp {

    private final Logger logger = LogManager.getLogger(ObjectDetectionApp.class);

    private final String DEFAULT_EVENT_TYPE = "event-vca-object-detection";
    private final String APP_ID = "KAI_X3_object_detection";
    private final String CONFIG_PATH = "configs/obj_detect_config.txt";
    private final String PLUGIN_DIR = "apps/ds_obj_detector/lib";
    private final String IMAGE_FOLDER= getImagesPath();


    @Override
    public String appId() {
        return APP_ID;
    }

    @Override
    public String vcaOutputTypeName() {
        return "evt_obj";
    }

    @Override
    public LocalizableText displayName() {
        return LocalizableText.create("object detection");
    }

    @Override
    public LocalizableText description() {
        return LocalizableText.create("description");
    }

    @Override
    public StreamType streamType() {
        return StreamType.RTSP_H264;
    }

    @Override
    public List<Parameter> parameters() {
        List<Parameter> parameterList= new ArrayList<Parameter>();
        parameterList.add(getPluginDir());
        parameterList.add(getConfigPath());
        return parameterList;
    }

    @Override
    public Map<String, String> environmentVariables() {
        return null;
    }

    @Override
    public List<ExtractedData> extract(Map outputData, ServerInfoProxy serverInfoProxy) throws InvalidOutputException {
        List<ExtractedData> extractedDataList = new ArrayList<ExtractedData>();

        try {
            JsonReader jsonReader = new JsonReader();
            jsonReader.setThrowRuntimeExceptionOnError(true);
            jsonReader.load(outputData);

            //String eventTypeName = jsonReader.getAsString("evt",DEFAULT_EVENT_TYPE);
            long evtTime = SharedUtils.parseISOTimeStamp(jsonReader.getAsString("timestamp",null));
            List<Map> objs = jsonReader.getAsList("objs",null);

            String fileName = Util.combine(IMAGE_FOLDER,String.format("%s.jpg",System.currentTimeMillis()));
            for(Map obj:objs){
                jsonReader.load(obj);
                String objLabel = jsonReader.getAsString("label",null);
                long firstSeen = SharedUtils.parseISOTimeStamp(jsonReader.getAsString("first_seen",null));
                long lastSeen = SharedUtils.parseISOTimeStamp(jsonReader.getAsString("last_seen",null));
                String jsonData = JsonBuilder.newInstance()
                                    .put("objLabel",objLabel)
                                    .put("firstSeen",firstSeen)
                                    .put("lastSeen",lastSeen)
                                    .put("fileName",fileName).stringify();
                ExtractedData extractedData = new ExtractedData(evtTime, DEFAULT_EVENT_TYPE, jsonData,true);
                extractedDataList.add(extractedData);



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


    private Parameter getPluginDir(){

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
                return LocalizableText.create("full path of plugin directory");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader, ServerInfoProxy serverInfoProxy) throws InvalidSettingsException {
                return Util.combine(KaiX3Implementation.getRuntimeLibraryFolder(),PLUGIN_DIR);
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


    private Parameter getConfigPath(){
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
                return LocalizableText.create("config file path");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader, ServerInfoProxy serverInfoProxy) throws InvalidSettingsException {
                return Util.combine(KaiX3Implementation.getRuntimeLibraryFolder(),CONFIG_PATH);
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

    private String getImagesPath(){
        String result = null;
        File file = Paths.get("").toAbsolutePath().toFile().getParentFile();
        result = Util.combine(file.getAbsolutePath(),"objImages");
        FileHelper.getDirectory(result);
        return result;
    }

}
