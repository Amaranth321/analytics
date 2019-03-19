package com.kaisquare.vca.event;

import com.kaisquare.vca.device.DeviceChannelPair;

/**
 * @author Aye Maung
 * @since v4.5
 */
public interface DispatchableEvent
{
    long getEventTime();

    String getEventType();

    DeviceChannelPair getCamera();

    String getJsonData();

    byte[] getBinaryData();
}
