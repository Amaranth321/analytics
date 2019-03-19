package com.kaisquare.vca.programs;

import com.kaisquare.vca.programs.kaix1.KaiX1Implementation;
import com.kaisquare.vca.programs.kaix2.KaiX2Implementation;
import com.kaisquare.vca.programs.kaix3.KaiX3Implementation;
import com.kaisquare.vca.programs.shared.Implementation;
import com.kaisquare.vca.system.ResourceManager;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum Program
{
    KAI_X1(new KaiX1Implementation()),
    KAI_X2(new KaiX2Implementation()),
    //add by renzongke
    KAI_X3(new KaiX3Implementation());

    private static final Logger logger = LogManager.getLogger();

    public static void runSetup()
    {
        for (Program program : values())
        {
            try
            {
                program.implementation.runServerStartupSetup();
            }
            catch (Exception e)
            {
                logger.error(e.toString(), e);
            }
        }
    }

    private final Implementation implementation;

    private Program(Implementation implementation)
    {
        this.implementation = implementation;
    }

    public Implementation getImplementation()
    {
        return implementation;
    }

    public String getProgramFolder()
    {
        return Util.combine(ResourceManager.getInstance().getVcaResourceFolder(), name());
    }
}
