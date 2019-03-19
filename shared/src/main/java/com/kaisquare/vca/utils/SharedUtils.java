package com.kaisquare.vca.utils;

import com.kaisquare.vca.shared.NormalizedPoint;
import com.kaisquare.vca.shared.PolygonRegion;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public final class SharedUtils
{
    private SharedUtils()
    {
        //utility class
    }

    public static boolean isNullOrEmpty(String s)
    {
        return s == null || s.length() == 0;
    }

    public static String combine(String basePath, String relativePath)
    {
        char trailingChar = basePath.charAt(basePath.length() - 1);
        if (trailingChar == '/' || trailingChar == '\\')
        {
            return basePath + relativePath;
        }
        else
        {
            return basePath + "/" + relativePath;
        }
    }

    public static List<String> toCommandList(Map<String, String> commandMap)
    {
        List<String> cmdList = new ArrayList<>();
        for (String key : commandMap.keySet())
        {
            cmdList.add(key);
            String value = commandMap.get(key);
            if (!SharedUtils.isNullOrEmpty(value))
            {
                cmdList.add(value);
            }
        }
        return cmdList;
    }

    public static List<PolygonRegion> parsePolygonRegions(List<Map> regionList) throws Exception
    {
        List<PolygonRegion> polygonList = new ArrayList<>();
        for (Map region : regionList)
        {

            //parse name and points fields
            String name = (String) region.get("name");
            List<Map> pointsMap = (List<Map>) region.get("points");

            //parse coordinates
            List<NormalizedPoint> pointList = new ArrayList<>();
            for (Map point : pointsMap)
            {
                Double x = Double.parseDouble(point.get("x").toString());
                Double y = Double.parseDouble(point.get("y").toString());
                NormalizedPoint npt = new NormalizedPoint(x, y);
                pointList.add(npt);
            }

            //construct PolygonRegion
            PolygonRegion polygonRegion = new PolygonRegion();
            polygonRegion.name = name;
            polygonRegion.points = pointList;
            polygonList.add(polygonRegion);
        }

        return polygonList;
    }

    //returns polygon string compatible with vca cntio parameter
    public static String getCntioString(List<PolygonRegion> polygons, String direction)
    {
        String cntioString = "";

        String poly1 = polygons.get(0).toString() + ";";
        String poly2 = polygons.get(1).toString() + ";";

        if (direction.equals("r1r2"))
        {
            cntioString += poly1 + poly2;
        }
        else
        {
            cntioString += poly2 + poly1;
        }

        return cntioString;
    }

    /**
     * @return time in milliseconds
     */
    public static long parseISOTimeStamp(String timestamp)
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss.SSSSSS'Z'").withZoneUTC();
        return DateTime.parse(timestamp, formatter).getMillis();
    }
}
