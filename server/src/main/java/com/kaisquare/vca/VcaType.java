package com.kaisquare.vca;

/**
 * This class is deprecated. Use appId instead.
 *
 * @author Aye Maung
 * @since v4.5
 */
@Deprecated
public enum VcaType
{
    /**
     * Business intelligence
     */
    TRAFFIC_FLOW("TRAFFIC"),
    PEOPLE_COUNTING("PCOUNTING"),
    CROWD_DETECTION("CROWD"),
    AUDIENCE_PROFILING("PROFILING"),

    /**
     * Security Analytics
     */
    AREA_INTRUSION("INTRUSION"),
    PERIMETER_DEFENSE("PERIMETER"),
    AREA_LOITERING("LOITERING"),
    OBJECT_COUNTING("OBJCOUNTING"),
    VIDEO_BLUR("VIDEOBLUR"),
    FACE_INDEXING("FACE");

    private final String vcaTypeName;

    public static VcaType parse(String vcaTypeName)
    {
        for (VcaType vcaType : values())
        {
            if (vcaType.getVcaTypeName().equals(vcaTypeName))
            {
                return vcaType;
            }
        }

        throw new IllegalArgumentException();
    }

    VcaType(String vcaTypeName)
    {
        this.vcaTypeName = vcaTypeName;
    }

    public String getVcaTypeName()
    {
        return vcaTypeName;
    }
}
