package net.company.orders.controller;

import net.company.orders.model.Coordinates;
import net.company.orders.model.MeasuringInstrument;
import net.company.orders.model.ObservationPoint;
import net.company.orders.model.PhysicalQuantity;
import net.company.orders.service.CoordinatesService;
import net.company.orders.service.MeasuringInstrumentService;
import net.company.orders.service.ObservationPointService;
import net.company.orders.service.PhysicalQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private ObservationPointService observationPointService;
    private MeasuringInstrumentService measuringInstrumentService;
    private PhysicalQuantityService physicalQuantityService;
    private CoordinatesService coordinatesService;

    @Autowired
    public AdminController(CoordinatesService coordinatesService, PhysicalQuantityService physicalQuantityService, ObservationPointService observationPointService, MeasuringInstrumentService measuringInstrumentService) {
        this.observationPointService = observationPointService;
        this.measuringInstrumentService = measuringInstrumentService;
        this.physicalQuantityService = physicalQuantityService;
        this.coordinatesService = coordinatesService;
    }

    /*
    *PhysicalQuantity
    */

    @GetMapping("/addPhysicalQuantity/{observationPointId}")
    @PreAuthorize("hasAuthority('admin')")
    public String addPhysicalQuantity(@PathVariable("observationPointId") Long observationPointId, PhysicalQuantity physicalQuantity, Model model) {
        List<MeasuringInstrument> measuringInstrumentList = observationPointService.findById(observationPointId).getMeasuringInstruments();
        model.addAttribute("measuringInstruments", measuringInstrumentList);
        model.addAttribute("observationPointId", observationPointId);
        return "admin/PhysicalQuantity/addPhysicalQuantity";
    }

    @PostMapping("/addPhysicalQuantity")
    @PreAuthorize("hasAuthority('admin')")
    public String addMeasuringInstrument(@Valid PhysicalQuantity physicalQuantity, Long measuringInstrumentId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/PhysicalQuantity/addPhysicalQuantity";
        }
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(measuringInstrumentId);
        physicalQuantity.setMeasuringInstrument(measuringInstrument);
        physicalQuantityService.savePhysicalQuantity(physicalQuantity);
        measuringInstrument.setPhysicalQuantity(physicalQuantity);
        measuringInstrumentService.saveMeasuringInstrument(measuringInstrument);
        Long observationPointId = measuringInstrument.getObservationPoint().getId();
        return "redirect:/getObservationPointById?id="+observationPointId;
    }
    @GetMapping("/deletePhysicalQuantity/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deletePhysicalQuantity(@PathVariable("id") Long id){
        MeasuringInstrument measuringInstrument = physicalQuantityService.findById(id).getMeasuringInstrument();
        Long observationPointId = measuringInstrument.getObservationPoint().getId();
        measuringInstrument.setPhysicalQuantity(null);
        measuringInstrumentService.saveMeasuringInstrument(measuringInstrument);
        physicalQuantityService.deleteById(id);
        return "redirect:/getObservationPointById?id="+observationPointId;
    }
    @GetMapping("/editPhysicalQuantity/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String editPhysicalQuantity(@PathVariable("id") Long id, Model model){
        PhysicalQuantity physicalQuantity = physicalQuantityService.findById(id);
        model.addAttribute("physicalQuantity", physicalQuantity);

        return "admin/PhysicalQuantity/editPhysicalQuantity";
    }
    @PostMapping("/editPhysicalQuantity")
    @PreAuthorize("hasAuthority('admin')")
    public String editPhysicalQuantity(@RequestParam Long physicalQuantity_id, Long measuringInstrumentId, @Valid PhysicalQuantity physicalQuantity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/PhysicalQuantity/editPhysicalQuantity";
        }
        physicalQuantity.setId(physicalQuantity_id);
        MeasuringInstrument measuringInstrument = measuringInstrumentService.findById(measuringInstrumentId);
        physicalQuantity.setMeasuringInstrument(measuringInstrument);
        physicalQuantityService.savePhysicalQuantity(physicalQuantity);
        Long observationPointId = physicalQuantity.getMeasuringInstrument().getObservationPoint().getId();
        return "redirect:/getObservationPointById?id="+observationPointId;
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
        if(physicalQuantityId!=0) measuringInstrument.setPhysicalQuantity(physicalQuantityService.findById(physicalQuantityId));
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
    public String addObservationPoint(@Valid ObservationPoint observationPoint, Coordinates coordinates, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/ObservationPoint/addObservationPoint";
        }
        coordinatesService.saveCoordinates(coordinates);
        observationPoint.setCoordinates(coordinates);
        observationPointService.saveObservationPoint(observationPoint);
        return "redirect:/";
    }
    @PostMapping("/deleteObservationPoint/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteProduct(@PathVariable("id") Long id){
        for (MeasuringInstrument measuringInstrument:
             observationPointService.findById(id).getMeasuringInstruments()) {
            if(measuringInstrument.getPhysicalQuantity() != null){
                physicalQuantityService.deleteById(measuringInstrument.getPhysicalQuantity().getId());
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
        return ("redirect:/");
    }

    /*
     *Coordinates
     */

    @GetMapping("/addCoordinates")
    @PreAuthorize("hasAuthority('admin')")
    public String addCoordinates(Coordinates coordinates) {

        return "admin/Coordinates/addCoordinates";
    }

    @PostMapping("/addCoordinates")
    @PreAuthorize("hasAuthority('admin')")
    public String addCoordinates(@Valid Coordinates coordinates, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/Coordinates/addCoordinates";
        }
        coordinatesService.saveCoordinates(coordinates);
        return "redirect:/";
    }
    @GetMapping("/deleteCoordinates/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteCoordinates(@PathVariable("id") Long id){
        coordinatesService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/editCoordinates/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String editCoordinates(@PathVariable("id") Long id, Model model){
        Coordinates coordinates = coordinatesService.findById(id);
        model.addAttribute("coordinates", coordinates);

        return "admin/Coordinates/editCoordinates";
    }
    @PostMapping("/editCoordinates")
    @PreAuthorize("hasAuthority('admin')")
    public String editCoordinates(@RequestParam Long coordinates_id, @Valid Coordinates coordinates, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/Coordinates/editCoordinates";
        }
        coordinates.setId(coordinates_id);
        coordinatesService.saveCoordinates(coordinates);
        return ("redirect:/");
    }
}
