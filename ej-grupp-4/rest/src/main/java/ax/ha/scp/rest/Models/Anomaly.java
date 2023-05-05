package ax.ha.scp.rest.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Anomaly {
    @NotNull
   // @Pattern(regexp="^[S]+[C]+[P]+[-]+[0-9]+[0-9]+[0-9]",message="The format on anomaly id is incorrect")
    @Id
    private String id;
    private String category;
    private String description;

    public Anomaly(String id, String category, String description){
        this.id = id;
        this.category = category;
        this.description = description;
    }

    public Anomaly(){

    }
    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    @Override
    public String toString(){
        return "Anomaly{" +"id =" + id + ", category =" + category + '\'' +
                ", description =" + description + '\'' + '}';
    }

}
