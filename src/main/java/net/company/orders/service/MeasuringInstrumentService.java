package net.company.orders.service;

import net.company.orders.model.MeasuringInstrument;
import net.company.orders.model.ObservationPoint;
import net.company.orders.repository.MeasuringInstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasuringInstrumentService {
    private MeasuringInstrumentRepository measuringInstrumentRepository;

    @Autowired
    public MeasuringInstrumentService(MeasuringInstrumentRepository measuringInstrumentRepository) {
        this.measuringInstrumentRepository = measuringInstrumentRepository;
    }
    public List<MeasuringInstrument> findAll(){
        return measuringInstrumentRepository.findAll();
    }

    public MeasuringInstrument findById(Long id){
        return measuringInstrumentRepository.findById(id).get();
    }

    public void saveMeasuringInstrument(MeasuringInstrument measuringInstrument){
        measuringInstrumentRepository.save(measuringInstrument);
    }

    public void deleteById(Long id){
        measuringInstrumentRepository.deleteById(id);
    }
}
