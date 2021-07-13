package net.company.orders.model;

import javax.persistence.Column;
import javax.persistence.Id;

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

        public long getTime() {
                return time;
        }

        public int getTimezone() {
                return timezone;
        }

        public double getTemperature2002() {
                return temperature2002;
        }

        public double getTemperature4402() {
                return temperature4402;
        }
}
