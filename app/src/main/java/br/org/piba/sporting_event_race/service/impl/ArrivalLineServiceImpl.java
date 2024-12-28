package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.ArrivalLineDtoToEntityConverter;
import br.org.piba.sporting_event_race.exception.IncorrectRequestException;
import br.org.piba.sporting_event_race.exception.RecordNotFoundException;
import br.org.piba.sporting_event_race.model.dto.ArrivalLineDTO;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.entity.ArrivalLine;
import br.org.piba.sporting_event_race.repository.ArrivalLineRepository;
import br.org.piba.sporting_event_race.service.ArrivalLineService;
import br.org.piba.sporting_event_race.service.ConsultAthlete;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArrivalLineServiceImpl implements ArrivalLineService {
    public static final String RECORD_NOT_FOUND = "Registro não encontrado";
    private final ArrivalLineDtoToEntityConverter converter;
    private final ArrivalLineRepository repository;
    private final ConsultAthlete consultAthlete;

    public ArrivalLineServiceImpl(ArrivalLineDtoToEntityConverter converter,
                                  ArrivalLineRepository repository,
                                  ConsultAthlete consultAthlete) {
        this.converter = converter;
        this.repository = repository;
        this.consultAthlete = consultAthlete;
    }

    @Override
    public ArrivalLineDTO save(final ArrivalLineDTO startRaceDTO) {
        final ArrivalLine toSave = Optional.ofNullable(converter.convert(startRaceDTO))
                .orElseThrow(() -> new IncorrectRequestException("Dados incorreto, entidade não criada"));
        final ArrivalLine saved = repository.save(toSave);
        return convertEntityToDto(saved,null);
    }

    @Override
    public List<ArrivalLineDTO> getBy(final String monitor, final Integer bibNumber) {
        final List<ArrivalLine> arrivalLine;
        if(hasMonitor(monitor) && hasBibNumber(bibNumber)){
            arrivalLine = repository.findByMonitorAndBibNumber(monitor, bibNumber);
        }else if(hasMonitor(monitor)){
            arrivalLine = repository.findByMonitor(monitor);
        }else if(hasBibNumber(bibNumber)){
            arrivalLine = repository.findByBibNumber(bibNumber);
        }else{
            arrivalLine = repository.findAll();
        }

        return arrivalLine.stream()
                .filter(Objects::nonNull)
                .map(this::mapWithAthleteName)
                .toList();
    }

    @Override
    public ArrivalLineDTO update(final ArrivalLineDTO startRaceDTO, final UUID idUuid) {
        ArrivalLine entityManaged = repository.findByIdUuid(idUuid)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        updateEntity(entityManaged, startRaceDTO);
        final ArrivalLine updated = repository.saveAndFlush(entityManaged);
        return mapWithAthleteName(updated);
    }

    @Override
    public void delete(final UUID idUuid) {
        final Integer id = repository.findByIdUuid(idUuid)
                .map(ArrivalLine::getId)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        repository.deleteById(id);
    }

    private static boolean hasBibNumber(Integer bibNumber) {
        return Objects.nonNull(bibNumber) && bibNumber > 0;
    }

    private static boolean hasMonitor(String monitor) {
        return Objects.nonNull(monitor) && !monitor.isBlank();
    }

    private void updateEntity(ArrivalLine entityManaged, ArrivalLineDTO startRaceDTO) {
        entityManaged.setBibNumber(startRaceDTO.chesterNumber());
        entityManaged.setTimeFinish(LocalTime.parse(startRaceDTO.hour(), DataTimeFormatterUtils.FORMATTER_HOUR));
        entityManaged.setMonitor(startRaceDTO.monitorName());
    }

    private ArrivalLineDTO mapWithAthleteName(final ArrivalLine entity) {
        final Optional<AthleteDTO> athlete = consultAthlete.getAthleteByBibNumber(entity.getBibNumber());
        return athlete
                .map(athleteDTO -> convertEntityToDto(entity, athleteDTO.name()))
                .orElseGet(() -> convertEntityToDto(entity, null));
    }

    private ArrivalLineDTO convertEntityToDto(final ArrivalLine entity, final String athleteName) {
        return new ArrivalLineDTO(entity.getIdUuid(),
                entity.getBibNumber(),
                entity.getTimeFinish().format(DataTimeFormatterUtils.FORMATTER_HOUR),
                entity.getMonitor(),
                athleteName);
    }
}
