package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.StartRaceDTO;

import java.util.List;
import java.util.UUID;

public interface StartRaceService {

    StartRaceDTO save(StartRaceDTO startRaceDTO);

    List<StartRaceDTO> getBy(String monitor, Integer bibNumber);

    StartRaceDTO update(StartRaceDTO startRaceDTO, UUID idUuid);

    void delete(UUID idUuid);

    List<StartRaceDTO> saveAll(List<StartRaceDTO> startRaceDTOS);
}
