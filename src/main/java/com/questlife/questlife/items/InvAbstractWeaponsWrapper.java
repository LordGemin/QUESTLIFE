package main.java.com.questlife.questlife.items;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * Created by Gemin on 12.05.2017.
 */
@XmlRootElement(name = "AbstractWeapons")
public class InvAbstractWeaponsWrapper {

    private List<AbstractWeapons> weapons;

    @XmlElement(name = "AbstractWeapon")
    public List<AbstractWeapons> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<AbstractWeapons> weapons) {
        this.weapons = weapons;
    }
}
