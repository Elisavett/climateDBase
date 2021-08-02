package net.company.orders.service;

import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.Entities.Sensor;
import net.company.orders.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
