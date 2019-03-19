package com.kaisquare.vca.event;

/**
 * @author: Aye Maung
 * @since v4.5
 */
public enum ErrorSource
{
    SERVER("VCA Server"),
    CORE_ENGINE("Core Engine"),
    VCA("VCA Executable");

    final String name;

    ErrorSource(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
