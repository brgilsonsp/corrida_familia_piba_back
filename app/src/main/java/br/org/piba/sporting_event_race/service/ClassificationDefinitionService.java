package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.enumaration.StatusTimerEnum;

import java.util.List;
import java.util.Map;

public interface ClassificationDefinitionService {

    List<ClassificationDTO> makeClassification(Map<StatusTimerEnum, List<AthleteData>> athletes);

    Map<StatusTimerEnum, List<AthleteData>> segregateListAthletes(List<AthleteData> originList);
}
