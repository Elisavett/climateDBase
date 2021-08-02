package net.company.orders.controller;

import net.company.orders.databases.DataBaseManager;
import net.company.orders.model.Entities.MeasuringInstrument;
import net.company.orders.model.Entities.ObservationPoint;
import net.company.orders.model.Entities.Sensor;
import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.TemperatureFromBase;
import net.company.orders.model.ViewModel;
import net.company.orders.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SiteController {


    private ObservationPointService observationPointService;
    private SensorService sensorService;
    private PhysicalQuantityService physicalQuantityService;
    private MeasuringInstrumentService measuringInstrumentService;
    private DbService dbService;

    @Autowired
    SiteController(MeasuringInstrumentService measuringInstrumentService, PhysicalQuantityService physicalQuantityService, ObservationPointService observationPointService, SensorService sensorService, DbService dbService){
        this.observationPointService = observationPointService;
        this.sensorService = sensorService;
        this.physicalQuantityService = physicalQuantityService;
        this.measuringInstrumentService = measuringInstrumentService;
        this.dbService = dbService;
    }

    @GetMapping("/")
    public String map(@RequestParam(value = "pqId", required=false) Long physicalQuantityId, Model model) {
        if(physicalQuantityId == null) physicalQuantityId = 1L;
        PhysicalQuantity physicalQuantity = physicalQuantityService.findById(physicalQuantityId);
        model.addAttribute("physicalQuantities", physicalQuantityService.findAll());
        List<TemperatureFromBase> obsPointValues = DataBaseManager.getDbData(physicalQuantity);
        model.addAttribute("json", observationPointService.getJsonCoordinate(obsPointValues));
        return "index";
    }
    @GetMapping("/getObservationPoints")
    public String getObservationPoints(Model model){
        List<ObservationPoint> observationPointList = observationPointService.findAll();
        model.addAttribute("observationPoints", observationPointList);
        return "observationPoints";
    }
    @GetMapping("/getObservationPointById")
    public String getObservationPointById(@RequestParam Long id, Model model){
        ObservationPoint observationPoint = observationPointService.findById(id);
        model.addAttribute("observationPoint", observationPoint);
        return "observationPoint";
    }
    @GetMapping("/getMeasuringInstrumentById")
    public String getMeasuringInstrumentById(@RequestParam Long id, @RequestParam Long obsPointId, Model model){
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(id);
        ViewModel observationPoint = observationPointService.findById(obsPointId);
        model.addAttribute("measuringInstrument", measuringInstrument);
        model.addAttribute("observationPoint", observationPoint);
        return "measuringInstrument";
    }
    @GetMapping("/getSensorById")
    public String getSensor(@RequestParam Long id, @RequestParam Long obsPointId, @RequestParam Long measurInstrId, Model model){
        Sensor sensor = sensorService.findById(id);
        ViewModel observationPoint = observationPointService.findById(obsPointId);
        ViewModel measuringInstrument = measuringInstrumentService.findById(measurInstrId);
        model.addAttribute("sensor", sensor);
        model.addAttribute("observationPoint", observationPoint);
        model.addAttribute("measuringInstrument", measuringInstrument);
        return "sensor";
    }
    @GetMapping("/getPhysicalQuantities")
    public String getPhysicalQuantities(Model model){
        List<PhysicalQuantity> physicalQuantities = physicalQuantityService.findAll();
        model.addAttribute("physicalQuantities", physicalQuantities);
        return "physicalQuantities";
    }
    @GetMapping("/getPhysicalQuantityForObsPoint")
    public String getPhysicalQuantityForObsPoint(@RequestParam Long id, Model model){
        ObservationPoint observationPoint = observationPointService.findById(id);
        model.addAttribute("observationPoint", observationPoint);
        return "physicalQuantityForObsPoint";
    }


}
