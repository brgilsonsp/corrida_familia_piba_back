package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.StatusFinishGeneralRaceDTOToEntityConverter;
import br.org.piba.sporting_event_race.exception.BusinessException;
import br.org.piba.sporting_event_race.model.dto.StatusFinishGeneralRaceDTO;
import br.org.piba.sporting_event_race.model.entity.StatusFinishGeneralRace;
import br.org.piba.sporting_event_race.repository.StatusFinishGeneralRaceRepository;
import br.org.piba.sporting_event_race.service.StatusFinishGeneralRaceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StatusFinishGeneralRaceServiceImpl implements StatusFinishGeneralRaceService {

    public static final String RACE_CLOSED = "CLOSED";
    private final StatusFinishGeneralRaceRepository repository;
    private final StatusFinishGeneralRaceDTOToEntityConverter converterDtoToEntity;

    public StatusFinishGeneralRaceServiceImpl(StatusFinishGeneralRaceRepository repository,
                                              StatusFinishGeneralRaceDTOToEntityConverter converterDtoToEntity) {
        this.repository = repository;
        this.converterDtoToEntity = converterDtoToEntity;
    }


    @Override
    public boolean raceClosed() {
        return repository.existsByStatusGeneralRace(RACE_CLOSED);
    }

    @Transactional
    @Override
    public void closeRace(StatusFinishGeneralRaceDTO statusFinishGeneralRaceDTO) {
        this.repository.deleteAll();
        StatusFinishGeneralRace entity = converterDtoToEntity.convert(statusFinishGeneralRaceDTO);
        if(Objects.isNull(entity)){
            throw new BusinessException("Erro ao criar a entidade para encerrar a corrida");
        }
        repository.save(entity);

    }
}
