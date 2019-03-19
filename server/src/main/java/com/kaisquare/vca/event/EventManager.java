package com.kaisquare.vca.event;

import com.kaisquare.vca.db.models.ErrorEvent;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.monitoring.StatusChangeEvent;
import com.kaisquare.vca.system.Connection;
import com.kaisquare.vca.utils.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Aye Maung
 * @since v4.5
 */
public enum EventManager
{
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    public static final String THRIFT_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private static final int ERROR_RECORD_TTL_MINS = 5;

    private final ConcurrentLinkedQueue<StatusChangeEvent> vcaStatusQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, Long> errorRecordMap = new ConcurrentHashMap<>();

    private static final Connection connection = Connection.getInstance();

    public static EventManager getInstance()
    {
        return INSTANCE;
    }

    public void queue(ErrorEvent errorEvent)
    {
        //too many cache-misses
        if (errorRecordMap.size() > 50)
        {
            logger.info("Clearing errorRecordMap ({} items)", errorRecordMap.size());
            errorRecordMap.clear();
        }

        String errorIdentifier = String.format("%s_%s", errorEvent.getInstanceId(), errorEvent.getErrorMsg());

        if (errorRecordMap.containsKey(errorIdentifier))
        {
            long lastSentTime = errorRecordMap.get(errorIdentifier);

            //ignore same errors within the last ERROR_RECORD_TTL_MINS
            if (!TimeUtil.isExpired(lastSentTime, ERROR_RECORD_TTL_MINS, TimeUnit.MINUTES))
            {
                return;
            }
        }

        errorEvent.save();
        errorRecordMap.put(errorIdentifier, System.currentTimeMillis());
    }

    public void queue(StatusChangeEvent statusChangeEvent)
    {
        synchronized (vcaStatusQueue)
        {
            if (vcaStatusQueue.contains(statusChangeEvent))
            {
                vcaStatusQueue.remove(statusChangeEvent);
            }

            vcaStatusQueue.add(statusChangeEvent);
        }
    }

    public void processDataEventQueue()
    {
        if (!connection.isEventServerAlive())
        {
            return;
        }

        ReportDataEvent dataEvent = ReportDataEvent.getOldest();
        while (dataEvent != null)
        {
            boolean result = EventThriftClient.getInstance().push(dataEvent);
            if (!result)
            {
                break;
            }

            dataEvent.delete();
            dataEvent = ReportDataEvent.getOldest();
        }
    }

    public void processErrorQueue()
    {
        if (!connection.isEventServerAlive())
        {
            return;
        }

        ErrorEvent errEvent = ErrorEvent.getOldest();
        while (errEvent != null)
        {
            boolean result = EventThriftClient.getInstance().push(errEvent);
            if (!result)
            {
                break;
            }

            errEvent.delete();
            errEvent = ErrorEvent.getOldest();
        }
    }

    public void processStatusQueue()
    {
        if (!connection.isEventServerAlive())
        {
            return;
        }

        while (!vcaStatusQueue.isEmpty())
        {
            StatusChangeEvent statusEvt = vcaStatusQueue.peek();
            boolean result = EventThriftClient.getInstance().push(statusEvt);
            if (!result)
            {
                break;
            }
            vcaStatusQueue.remove(statusEvt);
        }
    }

    EventManager()
    {
    }
}
