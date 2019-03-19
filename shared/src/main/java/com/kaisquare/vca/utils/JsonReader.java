package com.kaisquare.vca.utils;

import com.google.gson.Gson;
import com.kaisquare.vca.exceptions.InvalidJsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * Note: key names cannot have dots '.' because it is used as a special character to access nested maps.
 *
 * @author Aye Maung
 * @since v4.4
 */
public class JsonReader
{
    private static final Logger logger = LogManager.getLogger();
    private final Gson gson = new Gson();
    private boolean throwRuntimeExceptionOnError = false;

    private Map jsonMap = new LinkedHashMap();

    public JsonReader()
    {
    }

    public JsonReader(String jsonString) throws InvalidJsonException
    {
        this();
        load(jsonString);
    }

    public JsonReader(Map map)
    {
        this();
        load(map);
    }

    /**
     * Set this to 'true' to stop the current action (if an invalid field is unacceptable).
     * 'false' will simply log the error and return the specified default value.
     */
    public void setThrowRuntimeExceptionOnError(boolean throwRuntimeExceptionOnError)
    {
        this.throwRuntimeExceptionOnError = throwRuntimeExceptionOnError;
    }

    public synchronized void load(Map... configMaps)
    {
        if (configMaps == null || configMaps.length == 0)
        {
            throw new IllegalArgumentException("Empty config Maps");
        }

        this.jsonMap = configMaps[0];
        for (int i = 1; i < configMaps.length; i++)
        {
            this.jsonMap.putAll(configMaps[i]);
        }
    }

    public synchronized void load(String jsonString) throws InvalidJsonException
    {
        try
        {
            jsonMap = gson.fromJson(jsonString, Map.class);
        }
        catch (Exception e)
        {
        }

        if (jsonMap == null)
        {
            throw new InvalidJsonException();
        }
    }

    /**
     * @param key          parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultValue default value to return if the config entry is not found
     */
    public String getAsString(String key, String defaultValue)
    {
        try
        {
            String retStr = (String) getValue(key);
            return retStr.isEmpty() ? defaultValue : retStr;
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultValue;
        }
    }

    /**
     * @param key parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     */
    public String getAsJsonString(String key)
    {
        try
        {
            return gson.toJson(getValue(key));
        }
        catch (Exception e)
        {
            handleException(key, e);
            return null;
        }
    }

    /**
     * @param key          parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultValue default value to return if the config entry is not found
     */
    public int getAsInt(String key, int defaultValue)
    {
        try
        {
            Object value = getValue(key);
            if (value instanceof String)
            {
                return Integer.parseInt((String) value);
            }

            if (value instanceof Double)
            {
                return ((Double) value).intValue();
            }

            return (int) value;
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultValue;
        }
    }

    /**
     * @param key          parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultValue default value to return if the config entry is not found
     */
    public Long getAsLong(String key, Long defaultValue)
    {
        try
        {
            Object value = getValue(key);
            if (value instanceof String)
            {
                return Long.parseLong((String) value);
            }

            if (value instanceof Double)
            {
                return ((Double) value).longValue();
            }

            return (Long) value;
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultValue;
        }
    }

    /**
     * @param key          parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultValue default value to return if the config entry is not found
     */
    public Float getAsFloat(String key, Float defaultValue)
    {
        try
        {
            Double d = getAsDouble(key, defaultValue.doubleValue());
            return d.floatValue();
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultValue;
        }
    }

    /**
     * @param key          parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultValue default value to return if the config entry is not found
     */
    public Double getAsDouble(String key, Double defaultValue)
    {
        try
        {
            Object value = getValue(key);
            if (value instanceof String)
            {
                return Double.parseDouble((String) value);
            }

            return (Double) getValue(key);
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultValue;
        }
    }

    /**
     * @param key          parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultValue default value to return if the config entry is not found
     */
    public Boolean getAsBoolean(String key, Boolean defaultValue)
    {
        try
        {
            Object value = getValue(key);
            if (value instanceof String)
            {
                return Boolean.parseBoolean((String) value);
            }

            return (Boolean) getValue(key);
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultValue;
        }
    }

    /**
     * @param key         parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     * @param defaultList default value to return if the config entry is not found
     */
    public List getAsList(String key, List defaultList)
    {
        try
        {
            return (List) getValue(key);
        }
        catch (Exception e)
        {
            handleException(key, e);
            return defaultList;
        }
    }

    /**
     * @param key parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     */
    public InetSocketAddress getAsInetSocketAddress(String key)
    {
        try
        {
            Map addressMap = (Map) getValue(key);
            String host = (String) addressMap.get("host");
            int port = ((Double) addressMap.get("port")).intValue();
            return new InetSocketAddress(host, port);
        }
        catch (Exception e)
        {
            handleException(key, e);
            return null;
        }
    }

    public JsonReader getSubReader(String key)
    {
        JsonReader subReader = new JsonReader();
        try
        {
            String subJson = gson.toJson(getValue(key));
            subReader.load(subJson);
        }
        catch (Exception e)
        {
            handleException(key, e);
        }
        return subReader;
    }

    /**
     * @param key parameter name. Use dots '.' to access deeper levels (e.g. theme.cdn-path)
     */
    public boolean containsKey(String key)
    {
        try
        {
            Object obj = getValue(key);
            return (obj != null);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public synchronized Object getValue(String key) throws Exception
    {
        if (SharedUtils.isNullOrEmpty(key))
        {
            throw new Exception("Missing parameter name");
        }

        String errMsg = String.format("%s key doesn't exist", key);
        String[] splitKeys = key.split("\\.");
        Object currentObj = jsonMap.get(splitKeys[0]);
        if (currentObj == null)
        {
            throw new Exception(errMsg);
        }

        for (int i = 1; i < splitKeys.length; i++)
        {
            currentObj = ((Map) currentObj).get(splitKeys[i]);
            if (currentObj == null)
            {
                throw new Exception(errMsg);
            }
        }

        return currentObj;
    }

    public synchronized Set<String> getFullKeySet()
    {
        Set<String> configKeySet = new LinkedHashSet<>();
        compileKeys(jsonMap, "", configKeySet);
        return configKeySet;
    }

    public void handleException(String key, Exception e)
    {
        if (throwRuntimeExceptionOnError)
        {
            throw new RuntimeException(e);
        }
        else
        {
            logger.error("({}) {}", key, e.getMessage());
        }
    }

    private void compileKeys(Map map, String prefix, Set<String> configKeySet)
    {
        prefix = prefix.isEmpty() ? "" : prefix + ".";
        for (Object key : map.keySet())
        {
            String keyStr = key.toString();
            if (keyStr.indexOf(".") > 0)
            {
                continue;
            }

            String fullKey = prefix + keyStr;
            Object value = map.get(key);
            if (value instanceof Map)
            {
                compileKeys((Map) value, fullKey, configKeySet);
            }
            else
            {
                configKeySet.add(fullKey);
            }
        }
    }
}
