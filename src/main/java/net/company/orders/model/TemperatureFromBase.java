package net.company.orders.model;

import net.company.orders.model.Entities.ObservationPoint;


public class TemperatureFromBase{

        public TemperatureFromBase(ObservationPoint observationPoint){
                this.observationPoint = observationPoint;
        }

        private final ObservationPoint observationPoint;

        public void setMeasuredValue(String measuredValue) {
                this.measuredValue = measuredValue;
        }

        private String measuredValue;

        public String getMeasuredValue() {
                return measuredValue;
        }
        @Override
        public String toString() {
                String initialString = observationPoint.toString();
                //Удаление последнего симаола "}", чтобы представить одним json-объектом
                return initialString.substring(0, initialString.length() - 1) + ", " +
                        "\"measuredValue\":\"" + measuredValue + '\"' +
                        '}';
        }
}
