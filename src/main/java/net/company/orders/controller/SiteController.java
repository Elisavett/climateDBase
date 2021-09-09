package net.company.orders.controller;

import net.company.orders.databases.DataBaseManager;
import net.company.orders.model.Entities.MeasuringInstrument;
import net.company.orders.model.Entities.ObservationPoint;
import net.company.orders.model.Entities.Sensor;
import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.ValuesFromBase;
import net.company.orders.model.ViewModel;
import net.company.orders.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SiteController {


    private ObservationPointService observationPointService;
    private SensorService sensorService;
    private PhysicalQuantityService physicalQuantityService;
    private MeasuringInstrumentService measuringInstrumentService;

    @Autowired
    SiteController(MeasuringInstrumentService measuringInstrumentService, PhysicalQuantityService physicalQuantityService, ObservationPointService observationPointService, SensorService sensorService){
        this.observationPointService = observationPointService;
        this.sensorService = sensorService;
        this.physicalQuantityService = physicalQuantityService;
        this.measuringInstrumentService = measuringInstrumentService;
    }

    @GetMapping("/")
    public String map(@RequestParam(value = "pqId", required=false) Long physicalQuantityId, Model model) {
        if(physicalQuantityId == null) physicalQuantityId = 1L;
        PhysicalQuantity physicalQuantity = physicalQuantityService.findById(physicalQuantityId);
        model.addAttribute("physicalQuantities", physicalQuantityService.findAll());
        List<ValuesFromBase> obsPointValues = DataBaseManager.getDbData(physicalQuantity);
        model.addAttribute("json", observationPointService.getJsonCoordinate(obsPointValues));
        return "index";
    }
    @GetMapping("/getObservationPoints")
    public String getObservationPoints(Model model){
        List<ObservationPoint> observationPointList = observationPointService.findAll();
        observationPointList.sort(Comparator.comparing(ViewModel::getName));
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
        Map<Date, Double> chartData = DataBaseManager.countWeekTemperatures(3600, sensor);
        model.addAttribute("chartData2002", chartData);
        model.addAttribute("sensor", sensor);
        model.addAttribute("observationPoint", observationPoint);
        model.addAttribute("measuringInstrument", measuringInstrument);
        return "sensor";
    }
    @GetMapping("/getPhysicalQuantities")
    public String getPhysicalQuantities(Model model){
        List<PhysicalQuantity> physicalQuantities = physicalQuantityService.findAll();
        physicalQuantities.sort(Comparator.comparing(ViewModel::getName));
        model.addAttribute("physicalQuantities", physicalQuantities);
        return "physicalQuantities";
    }
    @GetMapping("/getPhysicalQuantityForObsPoint")
    public String getPhysicalQuantityForObsPoint(@RequestParam Long id, Model model){
        ObservationPoint observationPoint = observationPointService.findById(id);
        model.addAttribute("observationPoint", observationPoint);
        return "physicalQuantityForObsPoint";
    }
    @GetMapping("/showData")
    public ResponseEntity<Map<Date, Double>> showData(@RequestParam(value = "period", required = false) String period, @RequestParam Long sensorId, Model model) {
        Map<Date, Double> chartData = DataBaseManager.countWeekTemperatures(Integer.parseInt(period), sensorService.findById(sensorId));
        return ResponseEntity.ok().body(chartData);
    }


}
