package com.kaisquare.vca.programs.shared;

import com.kaisquare.vca.VcaInfo;

/**
 * @author Aye Maung
 * @since v4.5
 */
public interface Implementation
{
    void runServerStartupSetup();

    ProcessBuilderMaker newProcessBuilderMaker(VcaInfo vcaInfo) throws Exception;
}
