package com.kaisquare.vca.parsers;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  Aye Maung
 *
 */
class CrowdDetectionParser extends AbstractVcaParser {

    public CrowdDetectionParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception{

        //no user params for this
        Map<String, String> userParams = new LinkedHashMap<>();
        return userParams;
    }

}
