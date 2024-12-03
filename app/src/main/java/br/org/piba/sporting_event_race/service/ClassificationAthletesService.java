package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;

import java.util.List;

public interface ClassificationAthletesService {

    List<ClassificationDTO> getClassificationBy(String gender, String ageRange, String category);

}
