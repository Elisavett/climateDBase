package net.company.orders.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.company.orders.model.Coordinates;
import net.company.orders.repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatesService {
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    public CoordinatesService(CoordinatesRepository coordinatesRepository){
        this.coordinatesRepository = coordinatesRepository;
    }

    public List<Coordinates> findAll() { return coordinatesRepository.findAll();}

    public ArrayList<String> getJsonCoordinate(){
        Gson GSON = new GsonBuilder().create();
        ArrayList<String> json = new ArrayList<>();
        List<Coordinates> coordinatesList = findAll();
        for(Coordinates coordinates : coordinatesList) {
            coordinates.setNumber_group(1L);
            json.add(GSON.toJson(coordinates));
        }
        return json;
    }

    public Coordinates findById(Long id){
        return coordinatesRepository.findById(id).get();
    }

    public void saveCoordinates(Coordinates coordinates){
        coordinatesRepository.save(coordinates);
    }

    public void deleteById(Long id){
        coordinatesRepository.deleteById(id);
    }
}
