package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.AthleteEntityToDTOConverter;
import br.org.piba.sporting_event_race.exception.IncorrectRequestException;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.repository.AthleteRepository;
import br.org.piba.sporting_event_race.service.ConsultAthlete;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultAthleteService implements ConsultAthlete {private final AthleteRepository repository;
    private final AthleteEntityToDTOConverter converter;

    public ConsultAthleteService(AthleteRepository repository,
                                 AthleteEntityToDTOConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Optional<AthleteDTO> getAthleteByBibNumber(Integer bibNumber) {
        return repository.findByBibNumber(bibNumber)
                .map(converter::convert);
    }
}
