package net.company.orders.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "observation_point")
public class ObservationPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotEmpty
    private String name;
    @Column
    private String description;
    @OneToMany(mappedBy="observationPoint")
    private List<MeasuringInstrument> measuringInstruments = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;

    @Override
    public String toString() {
        String coords = coordinates != null ? ", \"coordinates\":" + coordinates.toString() : "";
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                 coords+
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MeasuringInstrument> getMeasuringInstruments() {
        return measuringInstruments;
    }

    public void setMeasuringInstruments(List<MeasuringInstrument> measuringInstruments) {
        this.measuringInstruments = measuringInstruments;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
