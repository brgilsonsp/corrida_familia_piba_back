package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.entity.StatusFinishGeneralRace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusFinishGeneralRaceRepository extends JpaRepository<StatusFinishGeneralRace, Integer> {

    boolean existsByStatusGeneralRace(String statusGeneralRace);
}
