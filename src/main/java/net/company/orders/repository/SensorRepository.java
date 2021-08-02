package net.company.orders.repository;

import net.company.orders.model.Entities.PhysicalQuantity;
import net.company.orders.model.Entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
