package main.java.com.questlife.questlife.town;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 12.04.2017.
 */
abstract class AbstractBuilding implements Serializable {
    String name;

    public String getName() {
        return name;
    }
}
