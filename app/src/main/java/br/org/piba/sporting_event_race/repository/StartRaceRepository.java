package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.entity.StartRace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StartRaceRepository extends JpaRepository<StartRace, Integer> {

    List<StartRace> findByMonitorName(String monitor);

    List<StartRace> findByBibNumber(Integer bibNumber);

    List<StartRace> findByMonitorNameAndBibNumber(String monitor, Integer bibNumber);

    Optional<StartRace> findByIdUuid(UUID idUuid);

    void deleteByBibNumberIn(List<Integer> bibNumber);

    void deleteByBibNumber(Integer bibNumber);

}
