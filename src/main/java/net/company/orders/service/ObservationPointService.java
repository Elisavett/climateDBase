package net.company.orders.service;

import net.company.orders.model.Entities.ObservationPoint;
import net.company.orders.model.ValuesFromBase;
import net.company.orders.repository.ObservationPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObservationPointService {
    private ObservationPointRepository observationPointRepository;

    @Autowired
    public ObservationPointService(ObservationPointRepository observationPointRepository){
        this.observationPointRepository = observationPointRepository;
    }
    public List<ObservationPoint> findAll(){
        return observationPointRepository.findAll();
    }

    public ObservationPoint findById(Long id){
        return observationPointRepository.findById(id).get();
    }

    public void saveObservationPoint(ObservationPoint observationPoint){
        observationPointRepository.save(observationPoint);
    }
    public ArrayList<String> getJsonCoordinate(List<ValuesFromBase> obsPointValues){
        ArrayList<String> json = new ArrayList<>();
        for(ValuesFromBase observationPoint : obsPointValues) {
            json.add(observationPoint.toString());
        }
        return json;
    }

    public void deleteById(Long id){
        observationPointRepository.deleteById(id);
    }
}