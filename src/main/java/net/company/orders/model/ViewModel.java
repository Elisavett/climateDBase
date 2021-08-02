package net.company.orders.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
public class ViewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column
    @NotEmpty
    protected String name;

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
}
