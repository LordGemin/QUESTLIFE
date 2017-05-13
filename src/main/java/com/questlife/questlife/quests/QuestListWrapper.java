package main.java.com.questlife.questlife.quests;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Wrapper class to save all quests in xml
 *
 * Created by Gemin on 12.05.2017.
 */
@XmlRootElement(name = "quests")
public class QuestListWrapper {

    private List<Quest> quests;

    @XmlElement(name = "quest")
    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }
}