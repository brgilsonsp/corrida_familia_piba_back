package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;

import java.util.List;
import java.util.UUID;

public interface AthleteService {

    List<AthleteDTO> getAthleteByDocumentAndPartialName(String document, String partialName);

    AthleteDTO saveAthlete(AthleteDTO athleteDTO);

    void deleteAthlete(UUID uuid);

    AthleteDTO updateAthlete(AthleteDTO athleteDTO, UUID idUuid);
}
