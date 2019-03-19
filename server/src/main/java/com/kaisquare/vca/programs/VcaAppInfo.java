package com.kaisquare.vca.programs;

import com.kaisquare.vca.sdk.VcaApp;
import com.kaisquare.vca.programs.kaix1.KaiX1Implementation;
import com.kaisquare.vca.VcaType;
import com.kaisquare.vca.shared.LocalizableText;

/**
 * @author Aye Maung
 * @since v4.5
 */
public class VcaAppInfo
{
    public final String appId;
    public final Program program;
    public final String version;
    public final LocalizableText displayName;
    public final LocalizableText description;

    public VcaAppInfo(VcaApp vcaApp, String version,Program program)
    {
        this.appId = vcaApp.appId();
        //this.program = Program.KAI_X2; //only this program supports apps
        this.program = program;
        this.version = version;
        this.displayName = vcaApp.displayName();
        this.description = vcaApp.description();
    }

    /**
     * just for old VCAs.
     */
    public VcaAppInfo(VcaType vcaType, String version)
    {
        this.appId = KaiX1Implementation.getAppIdFor(vcaType);
        this.program = Program.KAI_X1;
        this.version = version;
        LocalizableText displayText = LocalizableText.create(appId);
        LocalizableText descriptionText = LocalizableText.create(appId);
        switch (vcaType)
        {
            case AUDIENCE_PROFILING:
                displayText = LocalizableText
                        .create("Audience Profiling")
                        .add("zh-cn", "用户分析")
                        .add("zh-tw", "用戶分析");
                descriptionText = LocalizableText
                        .create("VCA to gather statistics about age/gender/emotions of the people in the monitoring area");
                break;

            case TRAFFIC_FLOW:
                displayText = LocalizableText.create("Human Traffic Flow")
                        .add("zh-cn", "客流量检测")
                        .add("zh-tw", "客流量檢測");
                descriptionText = LocalizableText
                        .create("VCA to gather statistics about how people move around in the monitoring area");
                break;

            case PEOPLE_COUNTING:
                displayText = LocalizableText
                        .create("People Counting")
                        .add("zh-cn", "客流统计")
                        .add("zh-tw", "客流統計");
                descriptionText = LocalizableText
                        .create("VCA to monitor IN/OUT/NET counts between two given regions.");
                break;

            case CROWD_DETECTION:
                displayText = LocalizableText
                        .create("Crowd Density")
                        .add("zh-cn", "客流密度")
                        .add("zh-tw", "客流密度");
                descriptionText = LocalizableText
                        .create("VCA to gather crowd density information");
                break;


            case AREA_INTRUSION:
                displayText = LocalizableText
                        .create("Intrusion Detection")
                        .add("zh-cn", "区域入侵")
                        .add("zh-tw", "區域入侵");
                descriptionText = LocalizableText
                        .create("VCA to detect area intrusions");
                break;

            case PERIMETER_DEFENSE:
                displayText = LocalizableText
                        .create("Perimeter Defense")
                        .add("zh-cn", "周界防御")
                        .add("zh-tw", "周界防禦");
                descriptionText = LocalizableText
                        .create("VCA to detect breaches to perimeter defense lines");
                break;

            case AREA_LOITERING:
                displayText = LocalizableText
                        .create("Loitering Detection")
                        .add("zh-cn", "徘徊检测")
                        .add("zh-tw", "徘徊檢測");
                descriptionText = LocalizableText
                        .create("VCA to detect persons loitering around the target area for too long");
                break;

            case OBJECT_COUNTING:
                displayText = LocalizableText
                        .create("Trip Wire Counting")
                        .add("zh-cn", "行线计数")
                        .add("zh-tw", "行線計數");
                descriptionText = LocalizableText
                        .create("VCA to detect if the people count has exceeded the limit");
                break;

            case VIDEO_BLUR:
                displayText = LocalizableText
                        .create("Camera Tampering")
                        .add("zh-cn", "摄像机干扰")
                        .add("zh-tw", "攝像機幹擾");
                descriptionText = LocalizableText
                        .create("VCA to detect camera tampering");
                break;

            case FACE_INDEXING:
                displayText = LocalizableText
                        .create("Face Indexing")
                        .add("zh-cn", "侦测脸部")
                        .add("zh-tw", "偵測臉部");
                descriptionText = LocalizableText
                        .create("VCA to capture peoples' faces in the monitoring area");
                break;
        }

        this.displayName = displayText;
        this.description = descriptionText;
    }

}
