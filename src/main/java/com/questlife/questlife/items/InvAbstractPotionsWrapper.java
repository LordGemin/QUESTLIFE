package main.java.com.questlife.questlife.items;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * Created by Gemin on 12.05.2017.
 */
@XmlRootElement(name = "AbstractPotions")
public class InvAbstractPotionsWrapper {

    private List<AbstractPotions> potions;

    @XmlElement(name = "AbstractPotion")
    public List<AbstractPotions> getPotions() {
        return potions;
    }

    public void setPotions(List<AbstractPotions> potions) {
        this.potions = potions;
    }
}
