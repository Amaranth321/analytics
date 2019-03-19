package com.kaisquare.vca.event;

/**
 * @author Aye Maung
 * @since v4.5
 */
public final class EventType
{
    public static final String VCA_TRAFFIC_FLOW = "event-vca-traffic";
    public static final String VCA_PEOPLE_COUNTING = "event-vca-people-counting";
    public static final String VCA_CROWD_DETECTION = "event-vca-crowd";
    public static final String VCA_PROFILING = "event-vca-audienceprofiling"; //final output
    public static final String VCA_FEED_PROFILING = "event-feed-profiling"; //real time

    public static final String VCA_INTRUSION = "event-vca-intrusion";
    public static final String VCA_PERIMETER_DEFENSE = "event-vca-perimeter";
    public static final String VCA_LOITERING = "event-vca-loitering";
    public static final String VCA_OBJECT_COUNTING = "event-vca-object-counting";
    public static final String VCA_VIDEO_BLUR = "event-vca-video-blur";
    public static final String VCA_FACE_INDEXING = "event-vca-face";

    public static final String VCA_STATUS_CHANGED = "event-vca-status-changed";

    public static final String ERROR = "event-vca-internal-error";

    private EventType()
    {
    }
}
