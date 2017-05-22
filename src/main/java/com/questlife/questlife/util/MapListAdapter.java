package main.java.com.questlife.questlife.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.AbstractMap;

/**
 *
 * Created by Gemin on 21.05.2017.
 */
public class MapListAdapter extends XmlAdapter<String, AbstractMap.SimpleEntry<String, Integer>>{

    @Override
    public AbstractMap.SimpleEntry<String, Integer> unmarshal(String v) throws Exception {
        if(v == null)
            return null;
        String[] str = v.split(";");
        return new AbstractMap.SimpleEntry<>(str[0],Integer.parseInt(str[1]));
    }

    @Override
    public String marshal(AbstractMap.SimpleEntry<String, Integer> v) throws Exception {
        if(v == null)
            return null;
        return v.getKey()+";"+v.getValue();
    }
}
