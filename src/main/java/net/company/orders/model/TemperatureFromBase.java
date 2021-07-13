package net.company.orders.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
public class TemperatureFromBase {
        @Id
        @Column(name = "time")
        long time;
        @Column(name = "timezone")
        int timezone;
        @Column(name = "`2002`")
        double temperature2002;
        @Column(name = "`4402`")
        double temperature4402;
}
