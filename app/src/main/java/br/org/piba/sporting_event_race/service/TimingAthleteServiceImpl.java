package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.dto.RegisterTimingDTO;
import br.org.piba.sporting_event_race.model.dto.RegistersTimingDTO;
import br.org.piba.sporting_event_race.repository.AthleteDataMockRepository;
import br.org.piba.sporting_event_race.repository.AthleteDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TimingAthleteServiceImpl implements TimingAthleteService{
    private final static Logger LOGGER= LoggerFactory.getLogger(TimingAthleteServiceImpl.class);
    private final AthleteDataRepository repository;
    private final StartRaceService startRaceService;

    public TimingAthleteServiceImpl(AthleteDataRepository repository, StartRaceService startRaceService) {
        this.repository = repository;
        this.startRaceService = startRaceService;
    }

    @Override
    public RegistersTimingDTO addTimingToAthlete(final RegistersTimingDTO timingsDTO){
        List<RegisterTimingDTO> registered = new ArrayList<>();
        timingsDTO.registersTiming()
                .stream()
                .map(this::mapperToAthleteData)
                .map(repository::update)
                .map(athleteData -> athleteData.orElseThrow(() -> new NoSuchElementException("Athlete not found")))
                .map(this::mapperToRegisterTimeDTO)
                .forEach(registered::add);

        return new RegistersTimingDTO(registered);
    }

    private AthleteData mapperToAthleteData(RegisterTimingDTO registerTimingDTO) {
        Optional<AthleteData> athleteByNumber = repository
                .getAthleteByNumber(registerTimingDTO.athleteNumber());
        if(athleteByNumber.isPresent()){
            LocalDateTime now = LocalDateTime.now();
            athleteByNumber.get().setArrivalTime(LocalDateTime.of(now.toLocalDate(), registerTimingDTO.arrivalTime()));
            athleteByNumber.get().setOperator(registerTimingDTO.operator());
            athleteByNumber.get().setStartTime(startRaceService.getStartRace());
            return athleteByNumber.get();
        }
        throw new NoSuchElementException("Athlete not found");
    }

    private RegisterTimingDTO mapperToRegisterTimeDTO(AthleteData athleteData) {

        return new RegisterTimingDTO(athleteData.getOperator(),
                athleteData.getNumber(),
                athleteData.getArrivalTime().toLocalTime());
    }

}
