package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Id {
    public Integer id;
    public Id(Integer id) {
        this.id = id;
    }
}
