package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.ArrivalLineDtoToEntityConverter;
import br.org.piba.sporting_event_race.exception.IncorrectRequestException;
import br.org.piba.sporting_event_race.exception.RecordNotFoundException;
import br.org.piba.sporting_event_race.model.dto.FinishRaceDTO;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.entity.FinishRace;
import br.org.piba.sporting_event_race.repository.ArrivalLineRepository;
import br.org.piba.sporting_event_race.service.FinishRaceService;
import br.org.piba.sporting_event_race.service.ConsultAthlete;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FinishRaceServiceImpl implements FinishRaceService {
    public static final String RECORD_NOT_FOUND = "Registro não encontrado";
    private final ArrivalLineDtoToEntityConverter converter;
    private final ArrivalLineRepository repository;
    private final ConsultAthlete consultAthlete;

    public FinishRaceServiceImpl(ArrivalLineDtoToEntityConverter converter,
                                 ArrivalLineRepository repository,
                                 ConsultAthlete consultAthlete) {
        this.converter = converter;
        this.repository = repository;
        this.consultAthlete = consultAthlete;
    }

    @Transactional
    @Override
    public FinishRaceDTO save(final FinishRaceDTO startRaceDTO) {
        final FinishRace entity = convertToEntity(startRaceDTO);
        Optional<FinishRace> entityBeforeSaved = findTImeFinishBefore(entity.getBibNumber(), entity.getTimeFinish());
        if(entityBeforeSaved.isPresent()){
            return convertEntityToDto(entityBeforeSaved.get(),null);
        }else{
            repository.deleteByBibNumber(entity.getBibNumber());
            final FinishRace saved = repository.save(entity);
            return convertEntityToDto(saved,null);
        }
    }

    private FinishRace convertToEntity(final FinishRaceDTO startRaceDTO){
        return  Optional.ofNullable(converter.convert(startRaceDTO))
                .orElseThrow(() -> new IncorrectRequestException("Dados incorreto, entidade não criada"));
    }

    private Optional<FinishRace> findTImeFinishBefore(final int bibNumber, final LocalTime timeFinishActual){
        return repository.findByBibNumber(bibNumber).stream()
                .filter(r -> r.getTimeFinish().isBefore(timeFinishActual))
                .findFirst();
    }

    @Override
    public List<FinishRaceDTO> getBy(final String monitor, final Integer bibNumber) {
        final List<FinishRace> finishRace;
        if(hasMonitor(monitor) && hasBibNumber(bibNumber)){
            finishRace = repository.findByMonitorNameAndBibNumber(monitor, bibNumber);
        }else if(hasMonitor(monitor)){
            finishRace = repository.findByMonitorName(monitor);
        }else if(hasBibNumber(bibNumber)){
            finishRace = repository.findByBibNumber(bibNumber);
        }else{
            finishRace = repository.findAll();
        }
        final List<AthleteDTO> listAthlete = getListAthlete(finishRace);

        return finishRace.stream()
                .filter(Objects::nonNull)
                .map(a -> mapWithAthleteName(a, listAthlete))
                .toList();
    }

    @Override
    public FinishRaceDTO update(final FinishRaceDTO startRaceDTO, final UUID idUuid) {
        FinishRace entityManaged = repository.findByIdUuid(idUuid)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        updateEntity(entityManaged, startRaceDTO);
        final FinishRace updated = repository.saveAndFlush(entityManaged);
        final List<AthleteDTO> listAthlete = getListAthlete(List.of(updated));
        return mapWithAthleteName(updated, listAthlete);
    }

    @Override
    public void delete(final UUID idUuid) {
        final Integer id = repository.findByIdUuid(idUuid)
                .map(FinishRace::getId)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        repository.deleteById(id);
    }

    private static boolean hasBibNumber(Integer bibNumber) {
        return Objects.nonNull(bibNumber) && bibNumber > 0;
    }

    private static boolean hasMonitor(String monitor) {
        return Objects.nonNull(monitor) && !monitor.isBlank();
    }

    private void updateEntity(FinishRace entityManaged, FinishRaceDTO startRaceDTO) {
        entityManaged.setBibNumber(startRaceDTO.bibNumber());
        entityManaged.setTimeFinish(LocalTime.parse(startRaceDTO.hour(), DataTimeFormatterUtils.FORMATTER_HOUR));
        entityManaged.setMonitorName(startRaceDTO.monitorName());
    }

    private FinishRaceDTO mapWithAthleteName(final FinishRace entity, final List<AthleteDTO> athleteDTOS) {

        return athleteDTOS.stream()
                .filter(this::isValidAthlete)
                .filter(a -> a.bibNumber().equals(entity.getBibNumber()))
                .findFirst()
                .map(athleteDTO -> convertEntityToDto(entity, athleteDTO.name()))
                .orElseGet(() -> convertEntityToDto(entity, null));
    }

    private List<AthleteDTO> getListAthlete(final List<FinishRace> finishRaces) {
        List<Integer> bibNumbers = finishRaces.stream()
                .map(FinishRace::getBibNumber)
                .toList();
        return this.consultAthlete.getListAthleteBy(bibNumbers);
    }

    private boolean isValidAthlete(final AthleteDTO athleteDTO){
        return Objects.nonNull(athleteDTO) &&
                Objects.nonNull(athleteDTO.name()) &&
                Objects.nonNull(athleteDTO.bibNumber()) &&
                athleteDTO.bibNumber() > 0;
    }

    private FinishRaceDTO convertEntityToDto(final FinishRace entity, final String athleteName) {
        return new FinishRaceDTO(entity.getIdUuid(),
                entity.getBibNumber(),
                entity.getTimeFinish().format(DataTimeFormatterUtils.FORMATTER_HOUR),
                entity.getMonitorName(),
                athleteName);
    }
}
