package sia.tacocloud.data.model;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
@Data
public class Ingredients {
    private  String id;
    private  String name;

    public void setType(Type type) {
        this.type = type;
    }

    private  Type type;

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Ingredients(){}

    public Ingredients(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Ingredients(String id) {
        this.id = id;
    }

    public enum Type {
            WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
