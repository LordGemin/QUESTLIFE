package main.java.com.questlife.questlife.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB to convert AttackTypes into readable
 *
 * Created by Gemin on 13.05.2017.
 */
public class AttackTypeAdapter extends XmlAdapter<String, AttackType> {

    @Override
    public AttackType unmarshal(String v) throws Exception {
        return AttackType.getField(v);
    }

    @Override
    public String marshal(AttackType v) throws Exception {
        return v.getFieldDescription();
    }
}
