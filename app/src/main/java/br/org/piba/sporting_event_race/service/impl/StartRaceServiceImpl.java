package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.StartRaceDtoToEntityConverter;
import br.org.piba.sporting_event_race.exception.IncorrectRequestException;
import br.org.piba.sporting_event_race.exception.RecordNotFoundException;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.dto.StartRaceDTO;
import br.org.piba.sporting_event_race.model.entity.Athlete;
import br.org.piba.sporting_event_race.model.entity.StartRace;
import br.org.piba.sporting_event_race.repository.StartRaceRepository;
import br.org.piba.sporting_event_race.service.ConsultAthlete;
import br.org.piba.sporting_event_race.service.StartRaceService;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class StartRaceServiceImpl implements StartRaceService {
    public static final String RECORD_NOT_FOUND = "Registro não encontrado";

    private final StartRaceRepository repository;
    private final StartRaceDtoToEntityConverter converterDtoToEntity;
    private final ConsultAthlete consultAthlete;

    public StartRaceServiceImpl(StartRaceRepository repository,
                                StartRaceDtoToEntityConverter converterDtoToEntity,
                                ConsultAthlete consultAthlete) {
        this.repository = repository;
        this.converterDtoToEntity = converterDtoToEntity;
        this.consultAthlete = consultAthlete;
    }

    @Override
    public StartRaceDTO save(final StartRaceDTO startRaceDTO) {
        StartRace entityToSave = Optional.ofNullable(converterDtoToEntity.convert(startRaceDTO))
                .orElseThrow(() -> new IncorrectRequestException("Dados incorreto, entidade não criada"));
        StartRace startRaceSaved = repository.saveAndFlush(entityToSave);

        return convertEntityToDto(startRaceSaved, null);
    }

    @Override
    public List<StartRaceDTO> getBy(final String monitor, final Integer bibNumber) {
        final List<StartRace> startRaces;
        if(hasMonitor(monitor) && hasBibNumber(bibNumber)){
            startRaces = repository.findByMonitorAndBibNumber(monitor, bibNumber);
        }else if(hasMonitor(monitor)){
            startRaces = repository.findByMonitor(monitor);
        }else if(hasBibNumber(bibNumber)){
            startRaces = repository.findByBibNumber(bibNumber);
        }else{
            startRaces = repository.findAll();
        }
        final List<AthleteDTO> listAthlete = getListAthlete(startRaces);

        return startRaces.stream()
                .filter(Objects::nonNull)
                .map(s -> mapWithAthleteName(s, listAthlete))
                .toList();
    }

    @Override
    public StartRaceDTO update(StartRaceDTO startRaceDTO, final UUID idUuid) {
        StartRace startRace = repository.findByIdUuid(idUuid)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        updateEntity(startRace, startRaceDTO);
        final StartRace updated = repository.saveAndFlush(startRace);
        final List<AthleteDTO> listAthlete = getListAthlete(List.of(updated));
        return mapWithAthleteName(updated, listAthlete);
    }

    @Override
    public void delete(final UUID idUuid) {
        final Integer id = repository.findByIdUuid(idUuid)
                .map(StartRace::getId)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));

        repository.deleteById(id);
    }

    private static boolean hasBibNumber(Integer bibNumber) {
        return Objects.nonNull(bibNumber) && bibNumber > 0;
    }

    private static boolean hasMonitor(String monitor) {
        return Objects.nonNull(monitor) && !monitor.isBlank();
    }

    private void updateEntity(StartRace entity, StartRaceDTO dto) {
        entity.setBibNumber(dto.bibNumber());
        entity.setTimeStart(LocalTime.parse(dto.arrivalTime(), DataTimeFormatterUtils.FORMATTER_HOUR));
        entity.setMonitor(dto.monitorName());
    }

    private List<AthleteDTO> getListAthlete(final List<StartRace> startRaces) {
        List<Integer> bibNumbers = startRaces.stream()
                .map(StartRace::getBibNumber)
                .toList();
        return this.consultAthlete.getListAthleteBy(bibNumbers);
    }

    private StartRaceDTO mapWithAthleteName(final StartRace entity, List<AthleteDTO> listAthlete) {
        return listAthlete.stream()
                .filter(this::isValidAthlete)
                .filter(a -> a.bibNumber().equals(entity.getBibNumber()))
                .findFirst()
                .map(athleteDTO -> convertEntityToDto(entity, athleteDTO.name()))
                .orElseGet(() -> convertEntityToDto(entity, null));
    }

    private boolean isValidAthlete(final AthleteDTO athleteDTO){
        return Objects.nonNull(athleteDTO) &&
                Objects.nonNull(athleteDTO.name()) &&
                Objects.nonNull(athleteDTO.bibNumber()) &&
                athleteDTO.bibNumber() > 0;
    }

    private StartRaceDTO convertEntityToDto(final StartRace entity, final String athleteName) {
        return new StartRaceDTO(entity.getIdUuid(),
                entity.getBibNumber(),
                entity.getTimeStart().format(DataTimeFormatterUtils.FORMATTER_HOUR),
                entity.getMonitor(),
                athleteName);
    }
}
