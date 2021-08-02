package net.company.orders.model.Entities;


import net.company.orders.model.ViewModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "measuring_instrument")
public class MeasuringInstrument extends ViewModel{
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="observation_point_id")
    private ObservationPoint observationPoint;
    @OneToMany(mappedBy="measuringInstrument")
    private List<Sensor> sensors = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObservationPoint getObservationPoint() {
        return observationPoint;
    }

    public void setObservationPoint(ObservationPoint observationPoint) {
        this.observationPoint = observationPoint;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> physicalQuantities) {
        this.sensors = physicalQuantities;
    }

    public void addSensor(Sensor sensor){
        sensors.add(sensor);
     }
}

