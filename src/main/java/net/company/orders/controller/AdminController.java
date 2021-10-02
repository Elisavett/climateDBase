package net.company.orders.controller;

import net.company.orders.model.Entities.*;
import net.company.orders.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private ObservationPointService observationPointService;
    private MeasuringInstrumentService measuringInstrumentService;
    private SensorService sensorService;
    private PhysicalQuantityService physicalQuantityService;


    @Autowired
    public AdminController(PhysicalQuantityService physicalQuantityService, SensorService sensorService, ObservationPointService observationPointService, MeasuringInstrumentService measuringInstrumentService) {
        this.observationPointService = observationPointService;
        this.measuringInstrumentService = measuringInstrumentService;
        this.sensorService = sensorService;
        this.physicalQuantityService = physicalQuantityService;
    }

    @GetMapping("/addPhysicalQuantity")
    @PreAuthorize("hasAuthority('admin')")
    public String addPhysicalQuantity(PhysicalQuantity physicalQuantity, Model model) {
        return "admin/PhysicalQuantity/addPhysicalQuantity";
    }
    @PostMapping("/addPhysicalQuantity")
    @PreAuthorize("hasAuthority('admin')")
    public String addPhysicalQuantity(@Valid PhysicalQuantity physicalQuantity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/PhysicalQuantity/addPhysicalQuantity";
        }
        physicalQuantityService.save(physicalQuantity);
        return "redirect:/getPhysicalQuantities";
    }
    @GetMapping("/editPhysicalQuantity/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String editPhysicalQuantity(@PathVariable("id") Long id, Model model) {
        model.addAttribute("physicalQuantity", physicalQuantityService.findById(id));
        return "admin/PhysicalQuantity/editPhysicalQuantity";
    }
    @PostMapping("/editPhysicalQuantity")
    @PreAuthorize("hasAuthority('admin')")
    public String editPhysicalQuantity(@RequestParam Long physicalQuantity_id, @Valid PhysicalQuantity physicalQuantity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/PhysicalQuantity/editPhysicalQuantity";
        }
        physicalQuantity.setId(physicalQuantity_id);
        physicalQuantityService.save(physicalQuantity);
        return "redirect:/getPhysicalQuantities";
    }
    @PostMapping("/deletePhysicalQuantity/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deletePhysicalQuantity(@PathVariable("id") Long id){
        List<Sensor> sensors = physicalQuantityService.findById(id).getSensors();
        for (Sensor s: sensors) {
            s.setPhysicalQuantity(null);
            sensorService.save(s);
        }
        physicalQuantityService.deleteById(id);
        return "redirect:/getPhysicalQuantities";
    }
    /*
    *Sensor
    */

    @GetMapping("/addSensor/{observationPointId}")
    @PreAuthorize("hasAuthority('admin')")
    public String addSensor(@PathVariable("observationPointId") Long observationPointId, Sensor sensor, Model model) {
        List<MeasuringInstrument> measuringInstrumentList = observationPointService.findById(observationPointId).getMeasuringInstruments();
        model.addAttribute("measuringInstruments", measuringInstrumentList);
        model.addAttribute("physicalQuantities", physicalQuantityService.findAll());
        model.addAttribute("observationPointId", observationPointId);
        return "admin/Sensor/addSensor";
    }

    @GetMapping("/addSensorToMeasInstr/{measuringInstrumentId}")
    @PreAuthorize("hasAuthority('admin')")
    public String addSensorToMeasInstr(@PathVariable("measuringInstrumentId") Long measuringInstrumentId, Sensor sensor, Model model) {
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(measuringInstrumentId);
        List<MeasuringInstrument> measuringInstrumentList = new ArrayList<MeasuringInstrument>();
        measuringInstrumentList.add(measuringInstrument);
        model.addAttribute("measuringInstruments", measuringInstrumentList);
        model.addAttribute("physicalQuantities", physicalQuantityService.findAll());
        model.addAttribute("observationPointId", measuringInstrument.getObservationPoint().getId());
        return "admin/Sensor/addSensor";
    }

    @PostMapping("/addSensor")
    @PreAuthorize("hasAuthority('admin')")
    public String addSensor(@Valid Sensor sensor, Long physicalQuantityId, Long measuringInstrumentId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/Sensor/addSensor";
        }
        PhysicalQuantity physicalQuantity = physicalQuantityService.findById(physicalQuantityId);
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(measuringInstrumentId);
        sensor.setMeasuringInstrument(measuringInstrument);
        sensor.setPhysicalQuantity(physicalQuantity);
        sensorService.save(sensor);
        Long obsPointId = measuringInstrument.getObservationPoint().getId();
        return "redirect:/getMeasuringInstrumentById?id="+measuringInstrumentId+"&obsPointId="+obsPointId;
    }
    @PostMapping("/deleteSensor/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteSensor(@PathVariable("id") Long id){
        MeasuringInstrument measuringInstrument = sensorService.findById(id).getMeasuringInstrument();
        Long measuringInstrumentId = measuringInstrument.getId();
        sensorService.deleteById(id);
        Long obsPointId = measuringInstrument.getObservationPoint().getId();
        return "redirect:/getMeasuringInstrumentById?id="+measuringInstrumentId+"&obsPointId="+obsPointId;
    }
    @GetMapping("/editSensor/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String editSensor(@PathVariable("id") Long id, Model model){
        Sensor sensor = sensorService.findById(id);
        model.addAttribute("sensor", sensor);
        model.addAttribute("measuringInstruments", measuringInstrumentService.findAll());
        model.addAttribute("physicalQuantities", physicalQuantityService.findAll());
        return "admin/Sensor/editSensor";
    }
    @PostMapping("/editSensor")
    @PreAuthorize("hasAuthority('admin')")
    public String editSensor(@RequestParam Long sensor_id, Long physicalQuantityId, Long measuringInstrumentId, @Valid Sensor sensor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/Sensor/editSensor";
        }
        sensor.setId(sensor_id);
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(measuringInstrumentId);
        PhysicalQuantity physicalQuantity = physicalQuantityService.findById(physicalQuantityId);
        sensor.setMeasuringInstrument(measuringInstrument);
        sensor.setPhysicalQuantity(physicalQuantity);
        sensorService.save(sensor);
        Long observationPointId = sensor.getMeasuringInstrument().getObservationPoint().getId();
        return "redirect:/getSensorById?id=" + sensor_id + "&obsPointId=" + observationPointId +"&measurInstrId="+measuringInstrumentId;
    }

    /*
     *MeasuringInstrument
     */

    @GetMapping("/addMeasuringInstrument")
    @PreAuthorize("hasAuthority('admin')")
    public String addMeasuringInstrument(MeasuringInstrument measuringInstrument, Model model) {
        List<ObservationPoint> observationPointList = observationPointService.findAll();
        model.addAttribute("observationPoints", observationPointList);
        return "admin/MeasuringInstrument/addMeasuringInstrument";
    }
    @GetMapping("/addMeasuringInstrumentToObsPoint/{obsPointId}")
    @PreAuthorize("hasAuthority('admin')")
    public String addMeasuringInstrumentToObsPoint(@PathVariable("obsPointId") Long id, MeasuringInstrument measuringInstrument, Model model) {
        List<ObservationPoint> observationPointList = new ArrayList<>();
        observationPointList.add(observationPointService.findById(id));
        model.addAttribute("observationPoints", observationPointList);
        return "admin/MeasuringInstrument/addMeasuringInstrument";
    }
    @PostMapping("/addMeasuringInstrument")
    @PreAuthorize("hasAuthority('admin')")
    public String addMeasuringInstrument(@Valid MeasuringInstrument measuringInstrument, Long observationPointId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/MeasuringInstrument/addMeasuringInstrument";
        }
        ObservationPoint observationPoint = observationPointService.findById(observationPointId);
        measuringInstrument.setObservationPoint(observationPoint);
        measuringInstrumentService.saveMeasuringInstrument(measuringInstrument);
        return "redirect:/getObservationPoints";
    }
    @PostMapping("/deleteMeasuringInstrument/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteMeasuringInstrument(@PathVariable("id") Long id){
        measuringInstrumentService.deleteById(id);
        return "redirect:/getObservationPoints";
    }
    @GetMapping("/editMeasuringInstrument/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String editMeasuringInstrument(@PathVariable("id") Long id, Model model){
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(id);
        List<ObservationPoint> observationPointList = observationPointService.findAll();
        //observationPointList.add(observationPointService.findById(measuringInstrument.getObservationPoint().getId()));
        model.addAttribute("observationPoints", observationPointList);
        model.addAttribute("measuringInstrument", measuringInstrument);

        return "admin/MeasuringInstrument/editMeasuringInstrument";
    }
    @PostMapping("/editMeasuringInstrument")
    @PreAuthorize("hasAuthority('admin')")
    public String editMeasuringInstrument(@RequestParam Long physicalQuantityId, @RequestParam Long measuringInstrument_id, @RequestParam Long observationPointId, @Valid MeasuringInstrument measuringInstrument, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/MeasuringInstrument/editMeasuringInstrument";
        }
        measuringInstrument.setId(measuringInstrument_id);
        measuringInstrument.setObservationPoint(observationPointService.findById(observationPointId));
        if(physicalQuantityId!=0) measuringInstrument.addSensor(sensorService.findById(physicalQuantityId));
        measuringInstrumentService.saveMeasuringInstrument(measuringInstrument);
        return ("redirect:/getObservationPoints");
    }

    /*
     *ObservationPoint
     */

    @GetMapping("/addObservationPoint")
    @PreAuthorize("hasAuthority('admin')")
    public String addObservationPoint(ObservationPoint observationPoint) {

        return "admin/ObservationPoint/addObservationPoint";
    }

    @PostMapping("/addObservationPoint")
    @PreAuthorize("hasAuthority('admin')")
    public String addObservationPoint(@Valid ObservationPoint observationPoint, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/ObservationPoint/addObservationPoint";
        }
        observationPointService.saveObservationPoint(observationPoint);
        return "redirect:/getObservationPoints";
    }
    @PostMapping("/deleteObservationPoint/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteProduct(@PathVariable("id") Long id){
        for (MeasuringInstrument measuringInstrument:
             observationPointService.findById(id).getMeasuringInstruments()) {
            if(measuringInstrument.getSensors().size() > 0){
                for(Sensor sensor : measuringInstrument.getSensors())
                sensorService.deleteById(sensor.getId());
            }
            measuringInstrumentService.deleteById(measuringInstrument.getId());
        }
        observationPointService.deleteById(id);
        return "redirect:/getObservationPoints";
    }
    @GetMapping("/editObservationPoint/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String editObservationPoint(@PathVariable("id") Long id, Model model){
        ObservationPoint observationPoint = observationPointService.findById(id);
        model.addAttribute("observationPoint", observationPoint);

        return "admin/ObservationPoint/editObservationPoint";
    }
    @PostMapping("/editObservationPoint")
    @PreAuthorize("hasAuthority('admin')")
    public String editObservationPoint(@RequestParam Long observationPoint_id, @Valid ObservationPoint observationPoint, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/ObservationPoint/editObservationPoint";
        }
        observationPoint.setId(observationPoint_id);
        observationPointService.saveObservationPoint(observationPoint);
        return ("redirect:/getObservationPoints");
    }

    @RequestMapping("/downloadSensorData")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> downloadSensorData(@RequestParam Long sensorId) {

        MediaType mediaType = new MediaType("text", "plain", Charset.defaultCharset());
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=SensorData.txt")
                // Content-Type
                .contentType(mediaType)
                .body(sensorService.getFileData(sensorId));
    }
}
