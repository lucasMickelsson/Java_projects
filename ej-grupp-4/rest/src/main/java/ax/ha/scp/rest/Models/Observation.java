package ax.ha.scp.rest.Models;

import jakarta.persistence.*;


@Entity
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_Anomaly", referencedColumnName = "id")
    private Anomaly anomaly;
    private String date;
    private String location;
    private String description;

    public Observation(Long id, Anomaly anomaly, String date, String location, String description) {
        this.id = id;
        this.anomaly = anomaly;
        this.date = date;
        this.location = location;
        this.description = description;
    }
    public Observation(){}

    public Long getId() {
        return id;
    }


    public Anomaly getAnomaly() {
        return anomaly;
    }

    public void setAnomaly(Anomaly anomaly) {
        this.anomaly = anomaly;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
