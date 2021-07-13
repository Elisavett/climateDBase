package net.company.orders.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "measuring_instrument")
public class MeasuringInstrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotEmpty
    private String name;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="observation_point_id")
    private ObservationPoint observationPoint;
    @OneToOne
    @JoinColumn(name = "physical_quantity_id")
    private PhysicalQuantity physicalQuantity;

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

    public ObservationPoint getObservationPoint() {
        return observationPoint;
    }

    public void setObservationPoint(ObservationPoint observationPoint) {
        this.observationPoint = observationPoint;
    }

    public PhysicalQuantity getPhysicalQuantity() {
        return physicalQuantity;
    }

    public void setPhysicalQuantity(PhysicalQuantity physicalQuantity) {
        this.physicalQuantity = physicalQuantity;
    }
}

