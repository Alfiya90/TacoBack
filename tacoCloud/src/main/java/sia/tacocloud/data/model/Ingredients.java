package sia.tacocloud.data.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Ingredients {
    @Id
    private  String id;
    private  String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private  Type type;

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }


    public enum Type {
            WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
