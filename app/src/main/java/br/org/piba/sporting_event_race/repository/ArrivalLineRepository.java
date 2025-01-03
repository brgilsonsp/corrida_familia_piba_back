package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.entity.ArrivalLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArrivalLineRepository extends JpaRepository<ArrivalLine, Integer> {

    List<ArrivalLine> findByMonitor(String monitor);

    List<ArrivalLine> findByBibNumber(Integer bibNumber);

    List<ArrivalLine> findByMonitorAndBibNumber(String monitor, Integer bibNumber);

    Optional<ArrivalLine> findByIdUuid(UUID idUuid);

    void deleteByBibNumber(Integer bibNumber);
}
