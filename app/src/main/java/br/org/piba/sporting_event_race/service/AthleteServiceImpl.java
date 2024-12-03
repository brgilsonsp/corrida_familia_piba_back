package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.exception.NumberAthleteUsedException;
import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.dto.AthleteFullNameRegisterDTO;
import br.org.piba.sporting_event_race.model.dto.AthleteNumberDTO;
import br.org.piba.sporting_event_race.repository.AthleteDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AthleteServiceImpl implements AthleteService{

    private final static Logger LOGGER= LoggerFactory.getLogger(AthleteServiceImpl.class);
    private final AthleteDataRepository repository;

    public AthleteServiceImpl(AthleteDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AthleteFullNameRegisterDTO> getListFullName(final String startName){
        List<AthleteFullNameRegisterDTO> athletes = new ArrayList<>();
        for(AthleteData athleteData : repository.getListBy(startName)){
            athletes.add(mapperToAthletesDTO(athleteData));
        }
        return athletes;
    }

    @Override
    public AthleteNumberDTO addNumber(final AthleteNumberDTO athletesNumber){
        Optional<AthleteData> athleteByNumber = this.repository.getAthleteByNumber(athletesNumber.number());
        if(athleteByNumber.isPresent() && athleteByNumber.get().getId().compareTo(athletesNumber.id()) != 0){
            throw new NumberAthleteUsedException("Número em uso");
        }
        AthleteData data = this.repository.getAthleteById(athletesNumber.id())
                .orElseThrow(() -> new NoSuchElementException("Athlete not found"));

        data.setNumber(athletesNumber.number());
        return this.repository.update(data)
                .map(this::mapperToAthleteNumberDTO)
                .orElseThrow(() -> new NoSuchElementException("Athlete not found"));
    }

    private AthleteFullNameRegisterDTO mapperToAthletesDTO(final AthleteData athleteData) {
        return new AthleteFullNameRegisterDTO(athleteData.getId(), athleteData.getName());
    }

    private AthleteNumberDTO mapperToAthleteNumberDTO(final AthleteData athleteData) {
        return new AthleteNumberDTO(athleteData.getId(), athleteData.getNumber());
    }
}
