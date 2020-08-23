package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Setting {
    public List<Item> outlets;
    public List<Item> targets;
    public List<Item> categories;
    public List<Brand> brands;

    public Setting() {
        outlets = new ArrayList<Item>();
        targets = new ArrayList<Item>();
        categories = new ArrayList<Item>();
        brands = new ArrayList<Brand>();
    }

    public void addOutlet(Integer id, String name) {
        Item item = new Item(id, name);
        outlets.add(item);
    }

    public void addTarget(Integer id, String name) {
        Item item = new Item(id, name);
        targets.add(item);
    }

    public void addCategorie(Integer id, String name) {
        Item item = new Item(id, name);
        categories.add(item);
    }

    public void addBrand(Brand brand) {
        brands.add(brand);
    }

    public Brand findBrand(Integer id) {
        for (Brand brand : brands) {
            if (brand.getId() == id) {
                return brand;
            }
        }
        return null;
    }
}
