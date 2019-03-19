package com.kaisquare.vca;

import com.kaisquare.vca.programs.Program;
import com.kaisquare.vca.programs.VcaAppService;
import com.kaisquare.vca.programs.shared.ProcessBuilderMaker;

/**
 * @author Aye Maung
 * @since v4.5
 */
enum VcaProcessFactory
{
    INSTANCE;

    public static VcaProcessFactory getInstance()
    {
        return INSTANCE;
    }

    public VcaProcess newProcess(VcaInfo vcaInfo) throws Exception
    {
        Program program = VcaAppService.getInstance().getProgram(vcaInfo.getAppId());
        ProcessBuilderMaker pbMaker = program.getImplementation().newProcessBuilderMaker(vcaInfo);
        return new VcaProcess(pbMaker);
    }
}
