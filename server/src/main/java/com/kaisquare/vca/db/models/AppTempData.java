package com.kaisquare.vca.db.models;

import com.google.code.morphia.query.Query;
import com.kaisquare.vca.db.MongoModel;

/**
 * This only allows one copy per instance
 *
 * @author Aye Maung
 * @since v4.5
 */
public class AppTempData extends MongoModel
{
    private String instanceId;
    private String jsonData;
    private long ttl;
    private long created;

    private AppTempData()
    {
        //for morphia
    }

    public static AppTempData find(String instanceId)
    {
        Query<AppTempData> query = q(AppTempData.class).filter("instanceId", instanceId);
        AppTempData appData = first(query);
        if (appData == null)
        {
            return null;
        }

        if (appData.hasExpired())
        {
            appData.delete();
            return null;
        }

        return appData;
    }

    public static void remove(String instanceId)
    {
        Iterable<AppTempData> iterable = q(AppTempData.class).filter("instanceId", instanceId).fetch();
        for (AppTempData appData : iterable)
        {
            appData.delete();
        }
    }

    public static void saveTempData(String instanceId, String jsonData, long ttl)
    {
        AppTempData appData = find(instanceId);
        if (appData != null)
        {
            appData.delete();
        }
        new AppTempData(instanceId, jsonData, ttl).save();
    }

    public static void removeExpired()
    {
        Iterable<AppTempData> iterable = q(AppTempData.class).fetch();
        for (AppTempData appData : iterable)
        {
            if (appData.hasExpired())
            {
                appData.delete();
            }
        }
    }

    private AppTempData(String instanceId, String jsonData, long ttl)
    {
        this.instanceId = instanceId;
        this.jsonData = jsonData;
        this.ttl = ttl;
        this.created = System.currentTimeMillis();
    }

    private boolean hasExpired()
    {
        return System.currentTimeMillis() - created > ttl;
    }

    public String getJsonData()
    {
        return jsonData;
    }

}
