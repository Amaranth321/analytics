package com.kaisquare.vca.parsers;

import com.google.gson.Gson;
import com.kaisquare.vca.misc.Utils;
import com.kaisquare.vca.models.PolygonRegion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:  Aye Maung
 */
public abstract class AbstractVcaParser
{
    private static final Logger logger = LogManager.getLogger();

    public static final int MASK_IMG_WIDTH = 640;
    public static final int MASK_IMG_HEIGHT = 480;
    public static final Gson gson = new Gson();

    private String uniqueName;
    private Map<String, String> defaultParams;
    private List<String> generatedTempFiles;

    private AbstractVcaParser()
    {
    }

    /**
     * @param uniqueName    to be used as temp file suffix
     * @param defaultParams default parameters
     */
    public AbstractVcaParser(String uniqueName, Map<String, String> defaultParams)
    {
        this.uniqueName = uniqueName;
        this.defaultParams = defaultParams;
        generatedTempFiles = new ArrayList<>();
    }

    /**
     * @param thresholds VcaInstance.thresholds
     * @return
     */
    public Map<String, String> getParameterMap(String thresholds) throws Exception
    {

        Map<String, String> fullMap = new LinkedHashMap<>();
        Map jsonThresholds = gson.fromJson(thresholds, Map.class);

        Map<String, String> userParams = getUserParams(jsonThresholds);
        Map<String, String> additionalParams = getAdditionalParams(jsonThresholds);

        //additionalParams will overwrite defaultParams if the same parameter exists
        fullMap.putAll(userParams);
        fullMap.putAll(defaultParams);
        fullMap.putAll(additionalParams);

        //check if gmask is specified
        String jpegGMask = generateGMaskImage(jsonThresholds);
        if (jpegGMask != null)
        {
            fullMap.put("gmask", jpegGMask);
        }

        return fullMap;

    }

    /**
     * @param paramsMap Map of parameter key pairs
     * @return
     */
    public String convertMapToCmdString(Map<String, String> paramsMap)
    {
        String cmdString = "";
        for (String key : paramsMap.keySet())
        {
            String value = paramsMap.get(key);
            cmdString += " -" + key;

            //some params don't have a value
            if (!value.isEmpty())
            {
                cmdString += " " + value;
            }
        }

        return cmdString;
    }

    /**
     * @return generated temporary files, if any, during parsing
     */
    public List<String> getGeneratedTempFiles()
    {
        return generatedTempFiles;
    }

    protected void addGeneratedTempFile(String file)
    {
        generatedTempFiles.add(file);
    }

    protected String getUniqueName()
    {
        return uniqueName;
    }

    private Map<String, String> getAdditionalParams(Map thresholdsMap) throws Exception
    {
        return (Map<String, String>) thresholdsMap.get("additional-params");
    }

    /**
     * returns null if gmask is not specified
     */
    private String generateGMaskImage(Map thresholdsMap)
    {
        try
        {
            Object polygonObj = thresholdsMap.get("gmask-regions");
            if (polygonObj == null)
            {
                return null;
            }

            List<PolygonRegion> regions = Utils.parsePolygonRegions(polygonObj);
            if (regions.size() == 0)
            {
                return null;
            }

            String filename = String.format("gmask_%s.jpg", getUniqueName());
            String gMaskJpeg = Utils.generateMaskFromPolygons(regions,
                    filename,
                    MASK_IMG_WIDTH,
                    MASK_IMG_HEIGHT,
                    false);
            if (!Utils.isNullOrEmpty(gMaskJpeg))
            {
                addGeneratedTempFile(gMaskJpeg);
            }

            return gMaskJpeg;

        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return null;
        }
    }

    protected abstract Map<String, String> getUserParams(Map thresholdsMap) throws Exception;
}