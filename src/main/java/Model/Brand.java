package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Brand {
    public Integer id;
    public String name;
    public List<Integer> categories;

    public Brand(Integer id, String name) {
        this.categories = new ArrayList<Integer>();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void addCategory(Integer id){
        categories.add(id);
    }
}
