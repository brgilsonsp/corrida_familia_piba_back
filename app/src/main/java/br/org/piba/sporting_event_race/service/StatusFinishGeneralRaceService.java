package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.StatusFinishGeneralRaceDTO;

public interface StatusFinishGeneralRaceService {

    boolean raceClosed();

    void closeRace(StatusFinishGeneralRaceDTO statusFinishGeneralRaceDTO);

}
