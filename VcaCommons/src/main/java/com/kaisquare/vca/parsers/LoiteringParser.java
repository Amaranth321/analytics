package com.kaisquare.vca.parsers;

import com.kaisquare.vca.misc.Utils;
import com.kaisquare.vca.models.PolygonRegion;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:  Aye Maung
 *
 */
class LoiteringParser extends AbstractVcaParser {

    public LoiteringParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception{

        String filename = String.format("mask_%s.jpg", getUniqueName());
        String jpegFile = null;

        //backward compatibility (< v4.4)
        if (thresholdsMap.containsKey("mask-base64"))
        {
            String maskFile = (String) thresholdsMap.get("mask-base64");
            jpegFile = Utils.saveMaskStringAsJpeg(maskFile, filename);
        }
        else
        {
            Object maskString = thresholdsMap.get("mask-regions");
            List<PolygonRegion> maskRegions = Utils.parsePolygonRegions(maskString);
            if(maskRegions.isEmpty())
            {
                throw new Exception("Invalid mask-regions: " + maskString);
            }

            jpegFile = Utils.generateMaskFromPolygons(maskRegions,
                                                      filename,
                                                      MASK_IMG_WIDTH,
                                                      MASK_IMG_HEIGHT,
                                                      true);
        }

        addGeneratedTempFile(jpegFile);

        Integer iDuration = Integer.parseInt(String.format("%1$.0f", thresholdsMap.get("duration")));
        String maskPercent = String.format("%1$.0f", thresholdsMap.get("maskPercent"));
        String maskInfo = String.format("fg=%s,img=%s,t=%s", maskPercent, jpegFile, iDuration);

        Map<String, String> userParams = new LinkedHashMap();
        userParams.put("mask", maskInfo);
        userParams.put("gmask", jpegFile);
        return userParams;
    }
}
