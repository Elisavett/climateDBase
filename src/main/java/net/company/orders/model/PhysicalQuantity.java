package net.company.orders.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
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

}
