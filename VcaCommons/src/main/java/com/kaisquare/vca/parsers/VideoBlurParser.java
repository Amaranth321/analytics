package com.kaisquare.vca.parsers;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  Aye Maung
 *
 */
class VideoBlurParser extends AbstractVcaParser {

    public VideoBlurParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception{
        String sharpness = String.format("%1$.0f", thresholdsMap.get("sharpness"));

        Map<String, String> userParams = new LinkedHashMap();
        userParams.put("detectblur", sharpness);
        return userParams;
    }
}
