package com.kaisquare.vca.programs.kaix1;

import com.google.gson.Gson;
import com.kaisquare.vca.VcaInfo;
import com.kaisquare.vca.db.models.ReportDataEvent;
import com.kaisquare.vca.event.EventType;
import com.kaisquare.vca.programs.kaix1.reportdata.ProfilingReportData;
import com.kaisquare.vca.programs.shared.CmdOutputListener;
import com.kaisquare.vca.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aye Maung
 * @since v4.5
 */
class ProfilingOutputListener extends CmdOutputListener
{
    public ProfilingOutputListener(VcaInfo vcaInfo)
    {
        super(vcaInfo);
    }

    @Override
    public void json(Map eventInfoMap)
    {
        //check event type
        VcaInfo vcaInfo = getVcaInfo();
        String eventType;
        if (eventInfoMap.get("evt").equals("ot"))
        {
            eventType = EventType.VCA_FEED_PROFILING;
        }
        else if (eventInfoMap.get("evt").equals("rt"))
        {
            eventType = EventType.VCA_PROFILING;
            logger.info("[{}] {}", vcaInfo, new Gson().toJson(eventInfoMap));
        }
        else
        {
            return;
        }

        //send one event for each face
        List<Map<String, String>> outputFaceList = (ArrayList<Map<String, String>>) eventInfoMap.get("tracks");
        for (Map<String, String> faceData : outputFaceList)
        {
            try
            {
                long faceTime = Util.parseVcaTimeOutput(faceData.get("time").toString());
                String id = faceData.get("id").toString();
                int duration = Math.round(Float.parseFloat(faceData.get("dur").toString()));

                Float gender = Float.parseFloat(String.valueOf(faceData.get("gen")));
                Float genderavg = Float.parseFloat(String.valueOf(faceData.get("genavg")));
                Float smile = Float.parseFloat(String.valueOf(faceData.get("smile")));
                Float smileavg = Float.parseFloat(String.valueOf(faceData.get("smileavg")));
                Float age = Float.parseFloat(String.valueOf(faceData.get("age")));
                Float ageavg = Float.parseFloat(String.valueOf(faceData.get("ageavg")));

                //report data
                ProfilingReportData reportData = new ProfilingReportData(eventType,
                                                                         vcaInfo.getInstanceId(),
                                                                         id,
                                                                         duration,
                                                                         gender,
                                                                         genderavg,
                                                                         smile,
                                                                         smileavg,
                                                                         age,
                                                                         ageavg);

                ReportDataEvent.queue(faceTime, vcaInfo.getCamera(), reportData);
            }
            catch (Exception e)
            {
                logger.error(e.toString(), e);
            }
        }
    }

    static class KaiX1Person{
        String name;
        String gender;
        String smileAvg;
        String ageAvg;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSmileAvg() {
            return smileAvg;
        }

        public void setSmileAvg(String smileAvg) {
            this.smileAvg = smileAvg;
        }

        public String getAgeAvg() {
            return ageAvg;
        }

        public void setAgeAvg(String ageAvg) {
            this.ageAvg = ageAvg;
        }

        public KaiX1Person(String name, String gender, String smileAvg, String ageAvg) {
            this.name = name;
            this.gender = gender;
            this.smileAvg = smileAvg;
            this.ageAvg = ageAvg;
        }
    }

    static class KaiX3Person{
        String name;
        String emotion;
        String age;
        boolean isKaix3;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmotion() {
            return emotion;
        }

        public void setEmotion(String emotion) {
            this.emotion = emotion;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public boolean isKaix3() {
            return isKaix3;
        }

        public void setKaix3(boolean kaix3) {
            isKaix3 = kaix3;
        }

        @Override
        public String toString() {
            return "KaiX3Person{" +
                    "name='" + name + '\'' +
                    ", emotion='" + emotion + '\'' +
                    ", age='" + age + '\'' +
                    ", isKaix3=" + isKaix3 +
                    '}';
        }
    }
    public static void main(String[] args) {
        Gson gson = new Gson();
        KaiX1Person kaiX1Person = new KaiX1Person("test","1","2","3");
        KaiX3Person kaiX3Person = gson.fromJson(gson.toJson(kaiX1Person),KaiX3Person.class);
        System.out.println(kaiX3Person);

    }

}
