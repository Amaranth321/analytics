package com.kaisquare.vca.monitoring;

import com.kaisquare.vca.process.ProcessStatus;

/**
 * @author Aye Maung
 * @since v4.5
 */
public interface StatusListener
{
    void changed(ProcessStatus newStatus);
}
