package com.kaisquare.vca.parsers;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  Aye Maung
 *
 */
class FaceIndexingParser extends AbstractVcaParser {

    public FaceIndexingParser(String uniqueName, Map<String, String> defaultParams) {
        super(uniqueName, defaultParams);
    }

    @Override
    protected Map<String, String> getUserParams(Map thresholdsMap) throws Exception {
        String faceCascadeFile = VcaParserFactory.resourceFolder + "cascade.xml";
        String dlibDataFile = VcaParserFactory.resourceFolder + "dlib.bin";
        String siamDataFile = VcaParserFactory.resourceFolder + "siam.bin";

        Map<String, String> userParams = new LinkedHashMap();
        userParams.put("ot", faceCascadeFile);
        userParams.put("otsiam", siamDataFile);
        userParams.put("otlm", "DLIB," + dlibDataFile);
        return userParams;
    }
}
