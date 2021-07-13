package net.company.orders.repository;

import net.company.orders.model.PhysicalQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicalQuantityRepository extends JpaRepository<PhysicalQuantity, Long> {
}
