package com.kaisquare.vca.event;

import com.kaisquare.events.thrift.EventDetails;
import com.kaisquare.platform.thrift.EventService;
import com.kaisquare.vca.system.Configs;
import com.kaisquare.vca.utils.ThriftUtil;
import com.kaisquare.vca.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.transport.TTransportException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

class EventThriftClient
{
    private static final Logger logger = LogManager.getLogger();
    private static final List<String> noLoggingTypes = Arrays.asList(EventType.VCA_FEED_PROFILING);
    private static  EventThriftClient instance;

    private ThriftUtil.Client<EventService.Iface> thriftClient = null;

    private EventThriftClient()
    {
        thriftClient = initClient();
    }

    public static EventThriftClient getInstance()
    {
        synchronized (EventThriftClient.class)
        {
            if (instance == null)
            {
                instance = new EventThriftClient();
            }
        }
        return instance;
    }

    private ThriftUtil.Client<EventService.Iface> initClient()
    {
        try
        {
            InetSocketAddress serverAddress = Configs.getInstance().getAsInetSocketAddress("event-server");
            logger.debug("Initializing EventThriftClient ({}:{})", serverAddress.getHostName(), serverAddress.getPort());

            //retry 3 times, with an interval of 1000 milliseconds between each try
            ThriftUtil.Client<EventService.Iface> client =  ThriftUtil.newServiceClient(
                    EventService.Iface.class,
                    EventService.Client.class,
                    serverAddress.getHostName(),
                    serverAddress.getPort(),
                    10000,
                    3,
                    1000);
            return client;
        }
        catch (TTransportException e)
        {
            logger.error(e.toString(), e);
            return null;
        }
    }

    public boolean push(DispatchableEvent dispatchable)
    {
        try
        {
            //prepare time
            DateTime dtTime = new DateTime(dispatchable.getEventTime(), DateTimeZone.UTC);
            String eventTime = dtTime.toString(DateTimeFormat.forPattern(EventManager.THRIFT_TIME_FORMAT));

            //prepare binary
            byte[] binaryData = dispatchable.getBinaryData();
            if (binaryData == null)
            {
                binaryData = new byte[0];
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(binaryData);

            //check json
            String jsonData = dispatchable.getJsonData();
            jsonData = jsonData == null ? "" : jsonData;

            EventDetails ed = new EventDetails();
            ed.setDeviceId(dispatchable.getCamera().getCoreDeviceId());
            ed.setChannelId(dispatchable.getCamera().getChannelId());
            ed.setData(jsonData);
            ed.setBinaryData(byteBuffer);
            ed.setId("0");
            ed.setTime(eventTime);
            ed.setType(dispatchable.getEventType());

            synchronized (thriftClient)
            {
                if (!noLoggingTypes.contains(dispatchable.getEventType()))
                {
                    logger.info("Sending [{}] {} {}",
                                dispatchable.getEventType(),
                                dispatchable.getCamera(),
                                Util.cutIfLong(jsonData, 300));
                }

                EventService.Iface eventServiceIface = thriftClient.getIface();
                return eventServiceIface.pushEvent(ed.getId(), ed);
            }
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            return false;
        }
    }
}
