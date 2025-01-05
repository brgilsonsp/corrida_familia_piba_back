package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.FinishRaceDTO;

import java.util.List;
import java.util.UUID;

public interface FinishRaceService {

    FinishRaceDTO save(FinishRaceDTO startRaceDTO);

    List<FinishRaceDTO> getBy(String monitor, Integer bibNumber);

    FinishRaceDTO update(FinishRaceDTO startRaceDTO, UUID idUuid);

    void delete(UUID idUuid);
}
