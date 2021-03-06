package com.kaisquare.vca.utils.java;

import java.io.Closeable;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class ACResource<T extends Closeable> implements AutoCloseable
{
    private final T resource;

    public ACResource(T resource)
    {
        this.resource = resource;
    }

    @Override
    public void close()
    {
        try
        {
            if (resource != null)
            {
                resource.close();
            }
        }
        catch (Exception e)
        {
        }
    }

    public T get()
    {
        return resource;
    }
}
