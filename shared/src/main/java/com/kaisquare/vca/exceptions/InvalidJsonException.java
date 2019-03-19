package com.kaisquare.vca.exceptions;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class InvalidJsonException extends Exception
{
    public InvalidJsonException()
    {
        super();
    }

    public InvalidJsonException(String file)
    {
        super(file);
    }

    public InvalidJsonException(Throwable e)
    {
        super(e);
    }
}
