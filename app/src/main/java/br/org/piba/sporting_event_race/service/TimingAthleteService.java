package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.RegistersTimingDTO;

public interface TimingAthleteService {

    RegistersTimingDTO addTimingToAthlete(RegistersTimingDTO timingsDTO);
}
