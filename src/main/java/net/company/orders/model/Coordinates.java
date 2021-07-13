package net.company.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

    public Long getNumber_group() {
        return number_group;
    }

    public void setNumber_group(Long number_group) {
        this.number_group = number_group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ObservationPoint getObservationPoint() {
        return observationPoint;
    }

    public void setObservationPoint(ObservationPoint observationPoint) {
        this.observationPoint = observationPoint;
    }
}
