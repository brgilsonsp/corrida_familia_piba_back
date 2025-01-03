package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.StartRaceGeneralDtoToEntityConverter;
import br.org.piba.sporting_event_race.converters.StartRaceGeneralEntityToDtoConverter;
import br.org.piba.sporting_event_race.exception.IncorrectRequestException;
import br.org.piba.sporting_event_race.model.dto.StartRaceDTO;
import br.org.piba.sporting_event_race.model.dto.StartRaceGeneralDTO;
import br.org.piba.sporting_event_race.model.entity.StartRaceGeneral;
import br.org.piba.sporting_event_race.repository.StartRaceGeneralRepository;
import br.org.piba.sporting_event_race.service.ConsultAthlete;
import br.org.piba.sporting_event_race.service.StartRaceGeneralService;
import br.org.piba.sporting_event_race.service.StartRaceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StartRaceGeneralServiceImpl implements StartRaceGeneralService {
    public static final String INCORRECT_DATA = "Dados incorreto. Erro ao criar a entidade";
    private final StartRaceGeneralRepository repository;
    private final StartRaceGeneralDtoToEntityConverter converterDtoToEntity;
    private final StartRaceGeneralEntityToDtoConverter converterEntityToDto;
    private final ConsultAthlete consultAthlete;
    private final StartRaceService startRaceService;

    public StartRaceGeneralServiceImpl(StartRaceGeneralRepository repository,
                                       StartRaceGeneralDtoToEntityConverter converterDtoToEntity,
                                       StartRaceGeneralEntityToDtoConverter converterEntityToDto, ConsultAthlete consultAthlete, StartRaceService startRaceService) {
        this.repository = repository;
        this.converterDtoToEntity = converterDtoToEntity;
        this.converterEntityToDto = converterEntityToDto;
        this.consultAthlete = consultAthlete;
        this.startRaceService = startRaceService;
    }

    @Transactional
    @Override
    public StartRaceGeneralDTO saveOrUpdate(final StartRaceGeneralDTO dto) {
        repository.deleteAll();
        final StartRaceGeneral startRaceGeneral = Optional.ofNullable(converterDtoToEntity.convert(dto))
                .orElseThrow(() -> new IncorrectRequestException(INCORRECT_DATA));
        final StartRaceGeneral startRaceGeneralSaved = repository.saveAndFlush(startRaceGeneral);
        final List<StartRaceDTO> listStartRaceDto = consultAthlete.getAllBibNumber().stream()
                .filter(Objects::nonNull)
                .map(b -> buildStartRaceDto(b, dto))
                .toList();
        startRaceService.saveAll(listStartRaceDto);
        return converterEntityToDto.convert(startRaceGeneralSaved);
    }

    private StartRaceDTO buildStartRaceDto(final Integer integer, final StartRaceGeneralDTO dto) {
        return new StartRaceDTO(null, integer, dto.arrivalTime(), dto.monitorName(), null);
    }
}
