package com.kaisquare.vca.utils;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class JsonBuilder
{
    private final Map<String, Object> dataMap;

    public static JsonBuilder newInstance()
    {
        return new JsonBuilder();
    }

    private JsonBuilder()
    {
        dataMap = new LinkedHashMap<>();
    }

    public JsonBuilder put(String key, Object value)
    {
        dataMap.put(key, value);
        return this;
    }

    public JsonBuilder putAll(Map<String, Object> map)
    {
        dataMap.putAll(map);
        return this;
    }

    public String stringify()
    {
        return new Gson().toJson(dataMap);
    }

    public Map<String, Object> asMap()
    {
        return new LinkedHashMap<>(dataMap);
    }

}
