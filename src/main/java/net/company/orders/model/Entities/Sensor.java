package net.company.orders.model.Entities;


import net.company.orders.model.ViewModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "sensor")
public class Sensor extends ViewModel {
    @Column
    private String measurement_mode;
    @NotEmpty
    @Column
    private Integer sensor_num;
    @NotEmpty
    @Column
    private String sensor_table;
    @NotEmpty
    @Column
    private String sensor_DB;

    @ManyToOne
    @JoinColumn(name="physicalQuantity_id")
    private PhysicalQuantity physicalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="measuring_instrument_id")
    private MeasuringInstrument measuringInstrument;


    public PhysicalQuantity getPhysicalQuantity() {
        return physicalQuantity;
    }

    public void setPhysicalQuantity(PhysicalQuantity physicalQuantities) {
        this.physicalQuantity = physicalQuantities;
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

    public String getMeasurement_mode() {
        return measurement_mode;
    }

    public void setMeasurement_mode(String measurement_mode) {
        this.measurement_mode = measurement_mode;
    }

    public MeasuringInstrument getMeasuringInstrument() {
        return measuringInstrument;
    }

    public void setMeasuringInstrument(MeasuringInstrument measuringInstrument) {
        this.measuringInstrument = measuringInstrument;
    }

    public Integer getSensor_num() {
        return sensor_num;
    }

    public void setSensor_num(Integer sensor_num) {
        this.sensor_num = sensor_num;
    }

    public String getSensor_table() {
        return sensor_table;
    }

    public void setSensor_table(String sensor_table) {
        this.sensor_table = sensor_table;
    }

    public String getSensor_DB() {
        return sensor_DB;
    }

    public void setSensor_DB(String sensor_DB) {
        this.sensor_DB = sensor_DB;
    }
}
