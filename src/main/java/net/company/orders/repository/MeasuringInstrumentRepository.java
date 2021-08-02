package net.company.orders.repository;

import net.company.orders.model.Entities.MeasuringInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasuringInstrumentRepository extends JpaRepository<MeasuringInstrument, Long> {
}
