package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.ArrivalLineDTO;

import java.util.List;
import java.util.UUID;

public interface ArrivalLineService {

    ArrivalLineDTO save(ArrivalLineDTO startRaceDTO);

    List<ArrivalLineDTO> getBy(String monitor, Integer bibNumber);

    ArrivalLineDTO update(ArrivalLineDTO startRaceDTO, UUID idUuid);

    void delete(UUID idUuid);
}
