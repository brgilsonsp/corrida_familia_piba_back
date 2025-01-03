package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.AthleteEntityToDTOConverter;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.repository.AthleteRepository;
import br.org.piba.sporting_event_race.service.ConsultAthlete;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultAthleteService implements ConsultAthlete {
    private final AthleteRepository repository;
    private final AthleteEntityToDTOConverter converter;

    public ConsultAthleteService(AthleteRepository repository,
                                 AthleteEntityToDTOConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public List<AthleteDTO> getListAthleteBy(List<Integer> bibNumber) {

        return repository.findByBibNumberIn(bibNumber)
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public List<Integer> getAllBibNumber() {
        return repository.findAllBibNumber();
    }
}
