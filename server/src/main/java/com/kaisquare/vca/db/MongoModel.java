package com.kaisquare.vca.db;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.query.Query;
import org.bson.types.ObjectId;

import java.util.Iterator;

/**
 * Note:
 * <p/>
 * This model is not as the same as Play's morphia model.
 * <p/>
 * Inherited classes must have a no-arg constructor. The constructor can be kept private.
 * The same thing applies to the classes of their fields.
 *
 * @author Aye Maung
 * @since v4.5
 */
public abstract class MongoModel
{
    @Id
    private ObjectId _id;

    public static Query q(Class<? extends MongoModel> clazz)
    {
        return DbManager.getInstance().getQuery(clazz);
    }

    public static <E extends MongoModel> E first(Query<? extends MongoModel> query)
    {
        Iterator<? extends MongoModel> iterator = query.iterator();
        if (iterator.hasNext())
        {
            return (E) iterator.next();
        }

        return null;
    }

    public ObjectId _getId()
    {
        return _id;
    }

    public void save()
    {
        DbManager.getInstance().save(this);
    }

    public void delete()
    {
        DbManager.getInstance().delete(this);
    }
}
