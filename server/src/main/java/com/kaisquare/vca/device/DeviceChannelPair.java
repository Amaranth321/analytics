package com.kaisquare.vca.device;

import com.kaisquare.vca.utils.Util;
import com.kaisquare.vca.utils.SharedUtils;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class DeviceChannelPair
{
    private String coreDeviceId;
    private String channelId;

    public DeviceChannelPair(String coreDeviceId, String channelId)
    {
        if (SharedUtils.isNullOrEmpty(coreDeviceId))
        {
            throw new NullPointerException();
        }
        this.coreDeviceId = coreDeviceId;
        this.channelId = channelId == null ? "" : channelId;
    }

    private DeviceChannelPair()
    {
    }

    public String getCoreDeviceId()
    {
        return coreDeviceId;
    }

    public String getChannelId()
    {
        return channelId;
    }

    @Override
    public String toString()
    {
        return String.format("%s:%s", coreDeviceId, channelId);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof DeviceChannelPair)
        {
            DeviceChannelPair other = (DeviceChannelPair) o;
            boolean dvcOk = this.getCoreDeviceId().equals(other.getCoreDeviceId());
            boolean chnOk = this.getChannelId().equals(other.getChannelId());
            return dvcOk && chnOk;
        }

        return false;
    }
}
