package com.kaisquare.vca.apps;

import com.kaisquare.vca.exceptions.InvalidOutputException;
import com.kaisquare.vca.exceptions.InvalidSettingsException;
import com.kaisquare.vca.sdk.Parameter;
import com.kaisquare.vca.sdk.ServerInfoProxy;
import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.shared.*;
import com.kaisquare.vca.utils.JsonBuilder;
import com.kaisquare.vca.utils.JsonReader;
import com.kaisquare.vca.utils.SharedUtils;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
@PluginImplementation
public class PeopleCountingApp implements VcaApp
{
    private static final String eventTypeName = "event-vca-people-counting";
    private static final long DATA_CACHE_TTL = TimeUnit.HOURS.toMillis(2);

    @Override
    public String appId()
    {
        return "peoplecount";
    }

    @Override
    public String vcaOutputTypeName() {
        return null;
    }

    @Override
    public LocalizableText displayName()
    {
        return LocalizableText
                .create("People Counting")
                .add("zh-cn", "KAI_X2 客流统计")
                .add("zh-tw", "KAI_X2 客流統計");
    }

    @Override
    public LocalizableText description()
    {
        return LocalizableText
                .create("This app monitors IN/OUT/NET counts between two given regions.");
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
        parameterList.add(getMinSizeParameter());
        parameterList.add(getMaxSizeParameter());

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
            int net = in - out;

            //check previous occupancy
            int previousOccupancy = 0;
            String jsonCache = serverInfoProxy.retrieveTempData();
            if (!SharedUtils.isNullOrEmpty(jsonCache))
            {
                JsonReader cacheReader = new JsonReader();
                cacheReader.load(jsonCache);
                previousOccupancy = cacheReader.getAsInt("occupancy", 0);
            }

            //save new occupancy
            int newOccupancy = previousOccupancy + net;
            String updatedCache = JsonBuilder.newInstance().put("occupancy", newOccupancy).stringify();
            serverInfoProxy.saveTempData(updatedCache, DATA_CACHE_TTL);

            //create json data in Platform's format
            String jsonData = JsonBuilder.newInstance()
                    .put("in", in)
                    .put("out", out)
                    .put("occupancy", newOccupancy)
                    .stringify();

            ExtractedData extractedData = new ExtractedData(evtTime, eventTypeName, jsonData);
            extractedDataList.add(extractedData);

            return  extractedDataList;
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
                        throw new Exception("There must be exactly two regions for people counting vca");
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

    private Parameter getMinSizeParameter()
    {
        return new Parameter()
        {
            @Override
            public String arg()
            {
                return "--min_pr";
            }

            @Override
            public boolean valueRequired()
            {
                return true;
            }

            @Override
            public LocalizableText description()
            {
                return LocalizableText.create("Minimum size of the person to be included in counting.");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader,
                                ServerInfoProxy serverInfoProxy) throws InvalidSettingsException
            {
                if (!configuredSettingsReader.containsKey("min-bounds"))
                {
                    return null;
                }

                try
                {
                    String jsonBounds = configuredSettingsReader.getAsJsonString("min-bounds");
                    RectangleRegion region = RectangleRegion.parse(jsonBounds);
                    return String.format("%sx%s", region.getWidth(), region.getHeight());
                }
                catch (Exception e)
                {
                    throw new InvalidSettingsException(e);
                }
            }

            @Override
            public boolean optional()
            {
                return true;
            }

            @Override
            public String defaultValue(ServerInfoProxy serverInfoProxy)
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Parameter getMaxSizeParameter()
    {
        return new Parameter()
        {
            @Override
            public String arg()
            {
                return "--max_pr";
            }

            @Override
            public boolean valueRequired()
            {
                return true;
            }

            @Override
            public LocalizableText description()
            {
                return LocalizableText.create("Maximum size of the person to be included in counting.");
            }

            @Override
            public String parse(JsonReader configuredSettingsReader,
                                ServerInfoProxy serverInfoProxy) throws InvalidSettingsException
            {
                if (!configuredSettingsReader.containsKey("max-bounds"))
                {
                    return null;
                }

                try
                {
                    String jsonBounds = configuredSettingsReader.getAsJsonString("max-bounds");
                    RectangleRegion region = RectangleRegion.parse(jsonBounds);
                    return String.format("%sx%s", region.getWidth(), region.getHeight());
                }
                catch (Exception e)
                {
                    throw new InvalidSettingsException(e);
                }
            }

            @Override
            public boolean optional()
            {
                return true;
            }

            @Override
            public String defaultValue(ServerInfoProxy serverInfoProxy)
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
