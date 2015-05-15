package example.laba.laba;

import com.orm.SugarRecord;

/**
 * Created by Brenda on 15/05/2015.
 */

public class Tag extends SugarRecord<Tag> {
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

