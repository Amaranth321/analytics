package com.kaisquare.vca.programs.shared;

import com.kaisquare.vca.VcaInfo;

/**
 * @author Aye Maung
 * @since v4.5
 */
public interface ProcessBuilderMaker
{
    VcaInfo getVcaInfo();

    ProcessBuilder make() throws Exception;

    CmdOutputListener newOutputListener() throws Exception;

    Runnable preProcessHook();

    Runnable postProcessHook();
}
