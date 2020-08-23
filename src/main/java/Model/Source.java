package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Source {

    public String name;
    public Double percent;

    public Source(String name, Double percent) {
        this.name = name;
        this.percent = percent;
    }

}
