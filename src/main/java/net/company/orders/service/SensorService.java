package net.company.orders.service;

import net.company.orders.model.Entities.MeasuringInstrument;
import net.company.orders.model.Entities.ObservationPoint;
import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.Entities.Sensor;
import net.company.orders.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository){
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {return sensorRepository.findAll();}

    public Sensor findById(Long id){
        return sensorRepository.findById(id).get();
    }

    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }

    public void deleteById(Long id){
        sensorRepository.deleteById(id);
    }

    public String getFileData(long sensorId){
        StringBuilder outputStr = new StringBuilder();

        Sensor sensor = findById(sensorId);
        MeasuringInstrument measuringInstrument = sensor.getMeasuringInstrument();
        ObservationPoint observationPoint = measuringInstrument.getObservationPoint();

        outputStr.append("Датчик: ").append(sensor.getName()).append("\n")
                .append("Физеская величина: ").append(sensor.getPhysicalQuantity().getName()).append("\n")
                .append("Средство измерения: ").append(measuringInstrument.getName()).append("\n")
                .append("Пункт наблюдения: ").append(observationPoint.getName()).append("\n\n");

        outputStr.append("Дата\t\t\tЗначение").append("\n");

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for(Map.Entry<Date, Double> mapEl : Sensor.chartData.entrySet()){
            outputStr.append(formatter.format(mapEl.getKey())).append("\t").append(mapEl.getValue());
            outputStr.append("\n");
        }
        return outputStr.toString();
    }
}
