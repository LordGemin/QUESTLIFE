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
        return Attributes.getField(v);
    }

    @Override
    public String marshal(Attributes v) throws Exception {
        return v.getFieldDescription();
    }
}
