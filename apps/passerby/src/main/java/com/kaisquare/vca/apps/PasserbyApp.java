package com.kaisquare.vca.apps;

import com.kaisquare.vca.exceptions.InvalidOutputException;
import com.kaisquare.vca.exceptions.InvalidSettingsException;
import com.kaisquare.vca.sdk.Parameter;
import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.shared.ExtractedData;
import com.kaisquare.vca.shared.LocalizableText;
import com.kaisquare.vca.shared.PolygonRegion;
import com.kaisquare.vca.shared.StreamType;
import com.kaisquare.vca.utils.JsonBuilder;
import com.kaisquare.vca.utils.JsonReader;
import com.kaisquare.vca.utils.SharedUtils;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
@PluginImplementation
public class PasserbyApp implements VcaApp
{
    private static final String eventTypeName = "event-vca-passerby";

    @Override
    public String appId()
    {
        return "passerby";
    }

    @Override
    public String vcaOutputTypeName() {
        return null;
    }

    @Override
    public LocalizableText displayName()
    {
        return LocalizableText
                .create("Passer-by Counting");
    }

    @Override
    public LocalizableText description()
    {
        return LocalizableText
                .create("This app monitors the passer-by count.");
    }

    @Override
    public StreamType streamType()
    {
        return StreamType.RTSP_H264;
    }

    @Override
    public List<Parameter> parameters()
    {
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(getCntioParameter());

        return parameterList;
    }

    @Override
    public Map<String, String> environmentVariables()
    {
        return new LinkedHashMap<>();
    }

    @Override
    public List<ExtractedData> extract(Map outputData, ServerInfoProxy serverInfoProxy) throws InvalidOutputException
    {
        //define return data list
        List<ExtractedData> extractedDataList = new ArrayList<>();

        //parse
        try
        {
            JsonReader jsonReader = new JsonReader();
            jsonReader.setThrowRuntimeExceptionOnError(true);
            jsonReader.load(outputData);

            //parse
            long evtTime = SharedUtils.parseISOTimeStamp(jsonReader.getAsString("time", null));
            int in = jsonReader.getAsInt("r1tor2", 0);
            int out = jsonReader.getAsInt("r2tor1", 0);

            //create json data in Platform's format
            String jsonData = JsonBuilder.newInstance()
                    .put("in", in)
                    .put("out", out)
                    .stringify();

            ExtractedData extractedData = new ExtractedData(evtTime, eventTypeName, jsonData);
            extractedDataList.add(extractedData);

            return extractedDataList;
        }
        catch (Exception e)
        {
            throw new InvalidOutputException(e);
        }
    }

    @Override
    public Runnable preProcessTask()
    {
        return null;
    }

    @Override
    public Runnable postProcessTask()
    {
        return null;
    }

    private Parameter getCntioParameter()
    {
        return new Parameter()
        {
            @Override
            public String arg()
            {
                return "--cntio";
            }

            @Override
            public boolean valueRequired()
            {
                return true;
            }

            @Override
            public LocalizableText description()
            {
                return LocalizableText.create("list of comma-separated polygon points, separated by semi-colons");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader,
                                ServerInfoProxy serverInfoProxy) throws InvalidSettingsException
            {
                try
                {
                    String direction = configuredSettingsReader.getAsString("direction", null);
                    List regionList = configuredSettingsReader.getAsList("regions", null);
                    List<PolygonRegion> regions = SharedUtils.parsePolygonRegions(regionList);
                    if (regions.size() != 2)
                    {
                        throw new Exception("There must be exactly two regions.");
                    }

                    return SharedUtils.getCntioString(regions, direction);
                }
                catch (Exception e)
                {
                    throw new InvalidSettingsException(e);
                }
            }

            @Override
            public boolean optional()
            {
                return false;
            }

            @Override
            public String defaultValue(ServerInfoProxy serverInfoProxy)
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
