package net.company.orders.repository;

import net.company.orders.model.Entities.ObservationPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationPointRepository extends JpaRepository<ObservationPoint, Long> {

}
