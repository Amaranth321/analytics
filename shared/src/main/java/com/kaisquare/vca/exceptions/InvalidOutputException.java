package com.kaisquare.vca.exceptions;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class InvalidOutputException extends Exception
{
    public InvalidOutputException()
    {
        super();
    }

    public InvalidOutputException(String msg)
    {
        super(msg);
    }

    public InvalidOutputException(Throwable e)
    {
        super(e);
    }
}
