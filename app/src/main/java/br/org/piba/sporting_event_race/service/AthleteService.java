package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.AthleteFullNameRegisterDTO;
import br.org.piba.sporting_event_race.model.dto.AthleteNumberDTO;

import java.util.List;

public interface AthleteService {

    List<AthleteFullNameRegisterDTO> getListFullName(String startName);

    AthleteNumberDTO addNumber(AthleteNumberDTO athletesNumber);
}
