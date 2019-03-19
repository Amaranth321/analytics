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
class ObjectCountingParser extends AbstractVcaParser {

    public ObjectCountingParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception {
        String direction = (String) thresholdsMap.get("direction");
        List<PolygonRegion> regions = Utils.parsePolygonRegions(thresholdsMap.get("regions"));
        if (regions.size() != 2) {
            throw new Exception("There must be exactly two polygons for object counting vca");
        }

        Map<String, String> userParams = new LinkedHashMap();
        userParams.put("cntio", Utils.getcntioString(regions, direction));

        return userParams;
    }

}
