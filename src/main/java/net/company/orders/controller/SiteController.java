package net.company.orders.controller;

import net.company.orders.databases.DataBaseManager;
import net.company.orders.model.ObservationPoint;
import net.company.orders.model.PhysicalQuantity;
import net.company.orders.service.CoordinatesService;
import net.company.orders.service.DbService;
import net.company.orders.service.ObservationPointService;
import net.company.orders.service.PhysicalQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SiteController {


    private ObservationPointService observationPointService;
    private CoordinatesService coordinatesService;
    private PhysicalQuantityService physicalQuantityService;
    private DbService dbService;

    @Autowired
    SiteController(ObservationPointService observationPointService, CoordinatesService coordinatesService, PhysicalQuantityService physicalQuantityService, DbService dbService){
        this.observationPointService = observationPointService;
        this.coordinatesService = coordinatesService;
        this.physicalQuantityService = physicalQuantityService;
        this.dbService = dbService;
    }

    @GetMapping("/")
    public String map(Model model) {
        model.addAttribute("temp", Math.round(DataBaseManager.getDbData()));
        model.addAttribute("json", observationPointService.getJsonCoordinate());
        return "index";
    }
    @GetMapping("/getObservationPoints")
    public String getObservationPoints(Model model){
        List<ObservationPoint> observationPointList = observationPointService.findAll();
        model.addAttribute("observationPoints", observationPointList);
        return "observationPoints";
    }
    @GetMapping("/getObservationPointById")
    public String getObservationPoints(@RequestParam Long id, Model model){
        ObservationPoint observationPoint = observationPointService.findById(id);
        model.addAttribute("observationPoint", observationPoint);
        return "physicalQuantityForObsPoint";
    }
    @GetMapping("/getPhysicalQuantities")
    public String getPhysicalQuantities(Model model){
        List<PhysicalQuantity> physicalQuantityList = physicalQuantityService.findAll();
        model.addAttribute("physicalQuantities", physicalQuantityList);
        return "physicalQuantities";
    }

}
