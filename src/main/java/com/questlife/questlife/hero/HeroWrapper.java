package main.java.com.questlife.questlife.hero;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
@XmlRootElement(name = "Heroes")
public class HeroWrapper {

        private List<Hero> heroes;

        @XmlElement(name = "Hero")
        public List<Hero> getHeroes() {
            return heroes;
        }

        public void setHeroes(List<Hero> heroes) {
            this.heroes = heroes;
        }
}
