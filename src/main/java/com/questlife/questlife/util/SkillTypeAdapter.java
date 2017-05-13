package main.java.com.questlife.questlife.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB binding to convert skill type to string and back
 *
 * Created by Gemin on 13.05.2017.
 */
public class SkillTypeAdapter extends XmlAdapter<String, SkillType> {
    @Override
    public SkillType unmarshal(String v) throws Exception {
        if (v.contains("Goal")) {
            return SkillType.GOALBASED;
        }
        if (v.contains("Time")) {
            return SkillType.TIMEBASED;
        }
        return null;
    }

    @Override
    public String marshal(SkillType v) throws Exception {
        return v.getFieldDescription();
    }
}
