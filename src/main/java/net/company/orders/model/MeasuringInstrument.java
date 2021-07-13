package net.company.orders.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
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
}

