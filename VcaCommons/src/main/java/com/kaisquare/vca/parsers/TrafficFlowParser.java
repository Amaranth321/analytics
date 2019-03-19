package com.kaisquare.vca.parsers;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  Aye Maung
 *
 */
class TrafficFlowParser extends AbstractVcaParser {

    public TrafficFlowParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception{
        Map<String, String> userParams = new LinkedHashMap<>();
        return userParams;
    }

}
