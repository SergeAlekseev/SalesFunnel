package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Stat {
    public List<Sale> sales;
    public List<Source> sources;

    public Stat(){
        sales = new ArrayList<Sale>();
        sources = new ArrayList<Source>();
    }

    public void addSale(String name, Double percent, Integer quantity){
        Sale sale = new Sale(name, percent, quantity);
        sales.add(sale);
    }

    public void addSource(String name, Double percent){
        Source source = new Source(name, percent);
        sources.add(source);
    }
 }
