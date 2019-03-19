package com.kaisquare.vca.shared;

import com.kaisquare.vca.exceptions.InvalidJsonException;
import com.kaisquare.vca.utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aye Maung
 */
public class RectangleRegion
{
    private static final Logger logger = LogManager.getLogger();
    private final String name;
    private final NormalizedPoint topLeftPoint;
    private final double width;
    private final double height;

    public static RectangleRegion parse(String jsonRegion) throws InvalidJsonException
    {
        JsonReader reader = new JsonReader(jsonRegion);
        reader.setThrowRuntimeExceptionOnError(true);
        try
        {
            String name = reader.getAsString("name", "");
            double topLeftX = reader.getAsDouble("topLeftPoint.x", 0d);
            double topLeftY = reader.getAsDouble("topLeftPoint.y", 0d);
            double width = reader.getAsDouble("width", 0d);
            double height = reader.getAsDouble("height", 0d);

            return new RectangleRegion(name, new NormalizedPoint(topLeftX, topLeftY), width, height);
        }
        catch (Exception e)
        {
            throw new InvalidJsonException(e);
        }
    }

    public RectangleRegion(String name,
                           NormalizedPoint topLeftPoint,
                           double width,
                           double height)
    {
        if (width < 0 || width > 1)
        {
            throw new IllegalArgumentException("width value must be between 0 and 1");
        }

        if (height < 0 || height > 1)
        {
            throw new IllegalArgumentException("height value must be between 0 and 1");
        }

        this.name = name;
        this.topLeftPoint = topLeftPoint;
        this.width = width;
        this.height = height;
    }

    public String getName()
    {
        return name;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public NormalizedPoint getTopLeftPoint()
    {
        return topLeftPoint;
    }

    public NormalizedPoint getBottomRightPoint()
    {
        double x2 = topLeftPoint.getX() + width;
        double y2 = topLeftPoint.getY() + height;

        x2 = x2 < 0 ? 0 : (x2 > 1 ? 1 : x2);
        y2 = y2 < 0 ? 0 : (y2 > 1 ? 1 : y2);
        NormalizedPoint bottomRight = null;
        try
        {
            bottomRight = new NormalizedPoint(x2, y2);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }

        return bottomRight;
    }
}
