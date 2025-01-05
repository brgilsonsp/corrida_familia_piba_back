package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.entity.FinishRace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArrivalLineRepository extends JpaRepository<FinishRace, Integer> {

    List<FinishRace> findByMonitorName(String monitor);

    List<FinishRace> findByBibNumber(Integer bibNumber);

    List<FinishRace> findByMonitorNameAndBibNumber(String monitor, Integer bibNumber);

    Optional<FinishRace> findByIdUuid(UUID idUuid);

    void deleteByBibNumber(Integer bibNumber);
}
