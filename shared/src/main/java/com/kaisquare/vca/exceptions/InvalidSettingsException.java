package com.kaisquare.vca.exceptions;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class InvalidSettingsException extends Exception
{
    public InvalidSettingsException()
    {
        super();
    }

    public InvalidSettingsException(String msg)
    {
        super(msg);
    }

    public InvalidSettingsException(Throwable e)
    {
        super(e);
    }
}
