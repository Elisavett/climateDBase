package net.company.orders.model.Entities;

import net.company.orders.model.ViewModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "observation_point")
public class ObservationPoint extends ViewModel {
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private String description;
    @OneToMany(mappedBy="observationPoint")
    private List<MeasuringInstrument> measuringInstruments = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"latitude\":\"" + latitude + '\"' +
                ", \"longitude\":\"" + longitude + '\"' +
                "}";
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

/*    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }*/
}
