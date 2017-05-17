package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.hero.Attributes;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
public class AttributesAdapter extends XmlAdapter<String, Attributes>{

    @Override
    public Attributes unmarshal(String v) throws Exception {
        if(!v.contains(";")) {
            return null;
        }

        String[] attr = v.split(";");

        Attributes a = Attributes.getField(attr[0]);
        assert a != null;
        a.setLevel(Integer.parseInt(attr[1]));
        a.setExperience(Integer.parseInt(attr[2]));
        a.setExperienceToNextLevel(Integer.parseInt(attr[3]));
        return a;
    }

    @Override
    public String marshal(Attributes v) throws Exception {
        String marshal = "";
        marshal += v.getFieldDescription(); // attr 0
        marshal += ";";
        marshal += v.getLevel();            // attr 1
        marshal += ";";
        marshal += v.getExperience();       // attr 2
        marshal += ";";
        marshal += v.getExperienceToNextLevel(); //attr 3
        return marshal;
    }
}
