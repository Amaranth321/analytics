package com.kaisquare.vca.misc;

import com.kaisquare.vca.parsers.VcaParserFactory;
import com.kaisquare.vca.models.NormalizedPoint;
import com.kaisquare.vca.models.PolygonRegion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public final class Utils
{
    private static final Logger logger = LogManager.getLogger();

    public static boolean isNullOrEmpty(String s)
    {
        return s == null || s.length() == 0;
    }

    public static String saveMaskStringAsJpeg(String maskString, String uniqueString)
    {
        String fileName = String.format("mask_%s.jpg", uniqueString);
        String outputFilename = getTempFileFolder() + fileName;

        try
        {
            byte[] jpegBinary = org.apache.commons.codec.binary.Base64.decodeBase64(maskString);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFilename));
            output.write(jpegBinary);
            output.close();
            return outputFilename;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTempFileFolder()
    {
        String tmpFolderName = VcaParserFactory.tempFolder;
        File folder = new File(tmpFolderName);
        if (!folder.exists())
        {
            folder.mkdirs();
        }

        return folder.getAbsolutePath() + "/";
    }

    public static Map<String, String> readDefaultParams(String propertiesFile) throws Exception
    {
        File f = new File(propertiesFile);
        if (!f.exists())
        {
            throw new Exception("File not found: " + f.getAbsolutePath());
        }

        Properties properties = new Properties();
        properties.load(new FileInputStream(f));
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        for (String key : properties.stringPropertyNames())
        {
            String value = Utils.isNullOrEmpty(properties.getProperty(key)) ? "" : properties.getProperty(key);
            paramMap.put(key, value);
        }

        return paramMap;
    }

    public static String getUniqueVcaName(String instanceId, String vcaType)
    {
        return String.format("%s_%s", instanceId, vcaType);
    }

    public static List<PolygonRegion> parsePolygonRegions(Object objRegionsMap) throws Exception
    {
        List<PolygonRegion> polygonList = new ArrayList<PolygonRegion>();

        List<Map> regionList = (List<Map>) objRegionsMap;
        for (Map region : regionList)
        {

            //parse name and points fields
            String name = (String) region.get("name");
            List<Map> pointsMap = (List<Map>) region.get("points");

            //parse coordinates
            List<NormalizedPoint> pointList = new ArrayList<NormalizedPoint>();
            for (Map point : pointsMap)
            {
                Double x = Double.parseDouble(point.get("x").toString());
                Double y = Double.parseDouble(point.get("y").toString());
                NormalizedPoint npt = new NormalizedPoint(x, y);
                pointList.add(npt);
            }

            //contruct PolygonRegion
            PolygonRegion polygonRegion = new PolygonRegion();
            polygonRegion.name = name;
            polygonRegion.points = pointList;
            polygonList.add(polygonRegion);
        }

        return polygonList;
    }

    //returns polygon string compatible with vca cntio parameter
    public static String getcntioString(List<PolygonRegion> polygons, String direction)
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
     * @param polygons            List of {@link com.kaisquare.vca.models.PolygonRegion}
     * @param outputFileName      output file name. Default temp directory will be used
     * @param imgWidth            Output Image width
     * @param imgHeight           Output Image height
     * @param maskOutsidePolygons 'true' will draw the outside of the polygons as a masked region (black color)
     * @return
     */
    public static String generateMaskFromPolygons(List<PolygonRegion> polygons,
                                                  String outputFileName,
                                                  int imgWidth,
                                                  int imgHeight,
                                                  boolean maskOutsidePolygons)
    {
        try
        {
            File generatedFile = new File(Utils.getTempFileFolder() + outputFileName);

            //Initialize Graphic and Buffer image
            BufferedImage bImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bImage.createGraphics();
            g2d.clearRect(0, 0, imgWidth, imgHeight);

            //check which regions to fill black and white
            if (maskOutsidePolygons)
            {
                g2d.setColor(Color.WHITE);
            }
            else
            {
                g2d.fillRect(0, 0, imgWidth, imgHeight);
                g2d.setColor(Color.BLACK);
            }

            //convert regions to awt Polygons
            for (PolygonRegion pReg : polygons)
            {
                Polygon jPolygon = new Polygon();
                for (NormalizedPoint npt : pReg.points)
                {
                    Double projectedX = npt.getX() * imgWidth;
                    Double projectedY = npt.getY() * imgHeight;
                    jPolygon.addPoint(projectedX.intValue(), projectedY.intValue());
                }

                g2d.fillPolygon(jPolygon);
            }

            //write to image
            g2d.drawImage(bImage, null, 0, 0);
            ImageIO.write(bImage, "JPEG", generatedFile);
            if (!generatedFile.exists())
            {
                return null;
            }

            return generatedFile.getAbsolutePath();
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return null;
        }
    }

    public static String whichFn()
    {
        try
        {
            StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
            String callerClass = steArray[2].getClassName();
            String callerMethod = steArray[2].getMethodName();

            String[] splitNames = callerClass.split("\\.");
            callerClass = splitNames[splitNames.length - 1];

            String errorOutput = String.format("In %s.%s() :", callerClass, callerMethod);
            return errorOutput;
        }
        catch (Exception e)
        {
            return " [unknown] ";
        }
    }
}
