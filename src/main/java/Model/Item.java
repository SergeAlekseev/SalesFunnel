package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Item {
    public Integer id;
    public String name;

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
