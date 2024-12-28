package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;

import java.util.Optional;

public interface ConsultAthlete {

    Optional<AthleteDTO> getAthleteByBibNumber(Integer bibNumber);
}
