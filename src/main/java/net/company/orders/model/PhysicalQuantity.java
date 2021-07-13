package net.company.orders.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "physical_quantity")
public class PhysicalQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotEmpty
    private String name;
    @Column
    @NotEmpty
    private String designation;
    @Column
    @NotEmpty
    private String unit;
    @Column
    @NotEmpty
    private  String db_designation;
    @Column
    private String measurement_mode;

    @OneToOne(mappedBy = "physicalQuantity")
    private MeasuringInstrument measuringInstrument;

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

    public String getDb_designation() {
        return db_designation;
    }

    public void setDb_designation(String db_designation) {
        this.db_designation = db_designation;
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
}
