package com.kaisquare.vca.parsers;

import com.kaisquare.vca.misc.Utils;
import com.kaisquare.vca.models.AnalyticsType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  Aye Maung
 */
public class VcaParserFactory
{
    private static final Logger logger = LogManager.getLogger();

    private static final VcaParserFactory parserFactory = new VcaParserFactory();
    private static final Map<String, Map<String, String>> defaultParameters = new LinkedHashMap<>();

    public static String resourceFolder;
    public static String tempFolder;

    private VcaParserFactory()
    {
    }

    public static VcaParserFactory getInstance()
    {
        return parserFactory;
    }

    /**
     * Call this to overwrite the default folders
     *
     * @param resourceFolder folder where vca executable and trained data files are located
     * @param tempFolder     folder for generating required files (e.g. mask files)
     */
    public void setDefaultFolders(String resourceFolder, String tempFolder)
    {
        VcaParserFactory.resourceFolder = resourceFolder + "/";
        VcaParserFactory.tempFolder = tempFolder + "/";
    }

    /**
     * This should be called only once when the server has started
     *
     * @param defaultFilesFolder folder where .properties files are located
     */
    public void loadDefaultParameters(String defaultFilesFolder)
    {

        loadProperties(AnalyticsType.AREA_INTRUSION, defaultFilesFolder + "/AreaIntrusion.properties");
        loadProperties(AnalyticsType.AUDIENCE_PROFILING, defaultFilesFolder + "/AudienceProfiling.properties");
        loadProperties(AnalyticsType.CROWD_DETECTION, defaultFilesFolder + "/CrowdDetection.properties");
        loadProperties(AnalyticsType.FACE_INDEXING, defaultFilesFolder + "/FaceIndexing.properties");
        loadProperties(AnalyticsType.AREA_LOITERING, defaultFilesFolder + "/Loitering.properties");
        loadProperties(AnalyticsType.OBJECT_COUNTING, defaultFilesFolder + "/ObjectCounting.properties");
        loadProperties(AnalyticsType.PEOPLE_COUNTING, defaultFilesFolder + "/PeopleCounting.properties");
        loadProperties(AnalyticsType.PERIMETER_DEFENSE, defaultFilesFolder + "/PerimeterDefence.properties");
        loadProperties(AnalyticsType.TRAFFIC_FLOW, defaultFilesFolder + "/TrafficFlow.properties");
        loadProperties(AnalyticsType.VIDEO_BLUR, defaultFilesFolder + "/VideoBlur.properties");

    }

    /**
     * @param analyticsType Analytics Type
     * @param instanceId    Vca Instance ID. Used to prevent duplicates of temporary files
     * @return AbstractVcaParser    actual parser based on type given
     * @throws Exception
     */
    public AbstractVcaParser createParser(String analyticsType, String instanceId) throws Exception
    {
        if (Utils.isNullOrEmpty(resourceFolder) || Utils.isNullOrEmpty(tempFolder))
        {
            throw new Exception("Set default folders first.");
        }

        if (defaultParameters.size() == 0)
        {
            throw new Exception("Load default properties files first.");
        }

        String uniqueName = Utils.getUniqueVcaName(instanceId, analyticsType);
        switch (analyticsType)
        {
            case AnalyticsType.AREA_INTRUSION:
                return new AreaIntrusionParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.AUDIENCE_PROFILING:
                return new AudienceProfilingParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.CROWD_DETECTION:
                return new CrowdDetectionParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.FACE_INDEXING:
                return new FaceIndexingParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.AREA_LOITERING:
                return new LoiteringParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.OBJECT_COUNTING:
                return new ObjectCountingParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.PEOPLE_COUNTING:
                return new PeopleCountingParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.PERIMETER_DEFENSE:
                return new PerimeterDefenceParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.TRAFFIC_FLOW:
                return new TrafficFlowParser(uniqueName, defaultParameters.get(analyticsType));

            case AnalyticsType.VIDEO_BLUR:
                return new VideoBlurParser(uniqueName, defaultParameters.get(analyticsType));

            default:
                throw new Exception("Unknown analyticsType");
        }

    }

    private void loadProperties(String analyticsType, String propertiesFile)
    {
        try
        {
            defaultParameters.put(analyticsType, Utils.readDefaultParams(propertiesFile));
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }

}
