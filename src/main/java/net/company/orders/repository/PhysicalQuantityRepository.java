package net.company.orders.repository;

import net.company.orders.model.Entities.PhysicalQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicalQuantityRepository extends JpaRepository<PhysicalQuantity, Long> {
    Optional<PhysicalQuantity> findByName(String name);
}
