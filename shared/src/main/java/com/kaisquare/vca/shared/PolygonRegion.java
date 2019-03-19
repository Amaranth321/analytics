package com.kaisquare.vca.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aye Maung
 */
public class PolygonRegion
{
    public String name;
    public List<NormalizedPoint> points;

    public PolygonRegion()
    {
        points = new ArrayList<>();
    }

    public String toString()
    {
        String retStr = "";
        for (int i = 0; i < points.size(); i++)
        {
            NormalizedPoint point = points.get(i);
            retStr += point.getX() + "," + point.getY();

            if (i < points.size() - 1)
            {
                //if not last
                retStr += ",";
            }
        }
        return retStr;
    }
}
