package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Sale {
    public String name;
    public Double percent;
    public Integer quantity;

    public Sale(String name, Double percent, Integer quantity) {
        this.name = name;
        this.percent = percent;
        this.quantity = quantity;
    }
}
