package net.company.orders.service;

import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.repository.PhysicalQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicalQuantityService {
    private PhysicalQuantityRepository physicalQuantityRepository;

    @Autowired
    public PhysicalQuantityService(PhysicalQuantityRepository physicalQuantityRepository){
        this.physicalQuantityRepository = physicalQuantityRepository;
    }

    public List<PhysicalQuantity> findAll() {return physicalQuantityRepository.findAll();}

    public PhysicalQuantity findById(Long id){
        return physicalQuantityRepository.findById(id).get();
    }

    public PhysicalQuantity findByName(String name){
        return physicalQuantityRepository.findByName(name).get();
    }

    public void save(PhysicalQuantity physicalQuantity){
        physicalQuantityRepository.save(physicalQuantity);
    }

    public void deleteById(Long id){
        physicalQuantityRepository.deleteById(id);
    }
}
