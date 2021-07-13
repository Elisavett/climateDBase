package net.company.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Transient
    private Long number_group;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @OneToOne(mappedBy = "coordinates")
    @JsonIgnore
    private ObservationPoint observationPoint;

    @Override
    public String toString() {
        return "{" +
                "\"number_group\":" + number_group +
                ", \"id\":" + id +
                ", \"latitude\":" + latitude +
                ", \"longitude\":" + longitude +
                '}';
    }
}
