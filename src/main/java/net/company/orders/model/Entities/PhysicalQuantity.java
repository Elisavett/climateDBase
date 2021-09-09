package net.company.orders.model.Entities;

import net.company.orders.model.ViewModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "physical_quantity")
public class PhysicalQuantity extends ViewModel {

    @Column
    @NotEmpty
    private String designation;

    @Column
    @NotEmpty
    private String unit;

    @OneToMany(mappedBy = "physicalQuantity")
    private List<Sensor> sensors;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensor(List<Sensor> sensor) {
        this.sensors = sensor;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PhysicalQuantity) {
            return this.id == ((PhysicalQuantity) obj).id;
        }
        else return super.equals(obj);
    }
}
