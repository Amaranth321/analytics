package com.kaisquare.vca.parsers;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  Aye Maung
 *
 */
class AudienceProfilingParser extends AbstractVcaParser {

    public AudienceProfilingParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception {

        String faceCascadeFile = VcaParserFactory.resourceFolder + "cascade.xml";
        String combineDataFile = VcaParserFactory.resourceFolder + "combine.bin";
        String dlibDataFile = VcaParserFactory.resourceFolder + "dlib.bin";
        String siamDataFile = VcaParserFactory.resourceFolder + "siam.bin";
        String caffeageFolder = VcaParserFactory.resourceFolder + "caffeage";

        Map<String, String> userParams = new LinkedHashMap();
        userParams.put("ot", faceCascadeFile);
        userParams.put("otap", combineDataFile);
        userParams.put("otsiam", siamDataFile);
        userParams.put("otlm", "DLIB," + dlibDataFile);
        userParams.put("otapcaf", caffeageFolder);

        return userParams;
    }
}
