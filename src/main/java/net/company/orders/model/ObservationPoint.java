package net.company.orders.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
}
