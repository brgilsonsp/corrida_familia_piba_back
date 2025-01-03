package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;

import java.util.List;
import java.util.Optional;

public interface ConsultAthlete {

    List<AthleteDTO> getListAthleteBy(List<Integer> bibNumber);

    List<Integer> getAllBibNumber();
}
