package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.bo.FilterClassificationBO;
import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.dto.StatusFinishGeneralRaceDTO;

import java.util.List;

public interface ClassificationService {

    List<ClassificationDTO> getClassification(FilterClassificationBO filterClassification);

    void closeRace(StatusFinishGeneralRaceDTO statusFinishGeneralRaceDTO);
}
