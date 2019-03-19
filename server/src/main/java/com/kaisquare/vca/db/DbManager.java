package com.kaisquare.vca.db;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.utils.SharedUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Collections;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum DbManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String DATABASE_NAME = "vca_server";

    private Datastore datastore;

    public static DbManager getInstance()
    {
        return INSTANCE;
    }

    public void initMongoDb(String username, String password)
    {
        try
        {
            //connect
            InetSocketAddress mongoAddress = Configs.getInstance().getAsInetSocketAddress("mongo");
            logger.info("Initializing Mongodb ({}:{})", mongoAddress.getHostName(), mongoAddress.getPort());

            Morphia morphia = new Morphia();

            //Connection info
            MongoClient mongoClient;
            if (SharedUtils.isNullOrEmpty(username) || SharedUtils.isNullOrEmpty(password))
            {
                mongoClient = new MongoClient(mongoAddress.getHostName(), mongoAddress.getPort());
            }
            else
            {
                ServerAddress serverAddress = new ServerAddress(mongoAddress.getHostName(), mongoAddress.getPort());
                MongoCredential credential = MongoCredential.createCredential(
                        username,
                        DATABASE_NAME,
                        password.toCharArray()
                );

                mongoClient = new MongoClient(serverAddress, Collections.singletonList(credential));
            }

            datastore = morphia.createDatastore(mongoClient, DATABASE_NAME);
            datastore.ensureIndexes();
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    public Query<? extends MongoModel> getQuery(Class<? extends MongoModel> modelClass)
    {
        return datastore.createQuery(modelClass);
    }

    public void save(MongoModel model)
    {
        datastore.save(model);
    }

    public void delete(MongoModel model)
    {
        delete(getQuery(model.getClass()).filter("_id", model._getId()));
    }

    public void delete(Query<? extends MongoModel> query)
    {
        datastore.delete(query);
    }
}
