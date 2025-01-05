package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.AthleteDtoToEntityConverter;
import br.org.piba.sporting_event_race.converters.AthleteEntityToDTOConverter;
import br.org.piba.sporting_event_race.exception.BusinessException;
import br.org.piba.sporting_event_race.exception.RecordNotFoundException;
import br.org.piba.sporting_event_race.exception.RecordDuplicateException;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.dto.AthleteTimerDTO;
import br.org.piba.sporting_event_race.model.entity.Athlete;
import br.org.piba.sporting_event_race.repository.AthleteRepository;
import br.org.piba.sporting_event_race.service.AthleteService;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import ch.qos.logback.core.util.StringUtil;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AthleteServiceImpl implements AthleteService {
    public static final String RECORD_DUPLICATE = "Já existe uma registro salvo com esses dados";
    public static final String RECORD_NOT_FOUND = "Registro não encontrado";
    private final AthleteRepository repository;
    private final AthleteEntityToDTOConverter converterEntityToDto;
    private final AthleteDtoToEntityConverter converterDtoToEntity;

    public AthleteServiceImpl(AthleteRepository repository,
                              AthleteEntityToDTOConverter converterEntityToDto,
                              AthleteDtoToEntityConverter converterDtoToEntity) {
        this.repository = repository;
        this.converterEntityToDto = converterEntityToDto;
        this.converterDtoToEntity = converterDtoToEntity;
    }

    @Override
    public List<AthleteDTO> getAthleteByDocumentAndPartialName(String document, String partialName) {
        final List<Athlete> athletes;
        if(StringUtil.notNullNorEmpty(document)){
            athletes = Collections.singletonList(repository.findFirstByDocument(document));
        }else if(StringUtil.notNullNorEmpty(partialName)){
            athletes = repository.findByAthleteNameStartingWith(partialName);
        }else{
            athletes = repository.findAll();
        }
        return athletes
                .stream()
                .filter(Objects::nonNull)
                .map(this::converterEntityToDto)
                .toList();
    }

    @Override
    public AthleteDTO saveAthlete(final AthleteDTO athleteDTO) {
        validaIfRecordExist(athleteDTO);
        try {
            final Athlete entity = converterDtoToEntity(athleteDTO);
            final Athlete athleteSaved = repository.saveAndFlush(entity);
            return converterEntityToDto(athleteSaved);
        }catch (Exception e){
            final String message = MessageFormat
                    .format("Erro ao salvar o atleta [{0}]", athleteDTO);
            throw new BusinessException(e, message);
        }
    }

    @Override
    public void deleteAthlete(final UUID uuid) {
        final Integer idAthlete = repository.findByIdUuid(uuid)
                .map(Athlete::getId)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        repository.deleteById(idAthlete);
    }

    @Override
    public AthleteDTO updateAthlete(AthleteDTO athleteDTO, UUID idUuid) {
        Athlete athlete = repository.findByIdUuid(idUuid)
                .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND));
        updateEntity(athleteDTO, athlete);
        final Athlete athleteUpdated = repository.saveAndFlush(athlete);
        return converterEntityToDto(athleteUpdated);
    }

    @Override
    public List<AthleteTimerDTO> getAthleteTimer() {
        return repository.findAllAthleteTimer();
    }

    private void validaIfRecordExist(AthleteDTO athleteDTO) {
        if(repository.existsByDocument(athleteDTO.document())){
            throw new RecordDuplicateException(RECORD_DUPLICATE);
        }
    }

    private void updateEntity(AthleteDTO athleteDTO, Athlete athleteSaved) {
        athleteSaved.setAthleteName(athleteDTO.name());
        athleteSaved.setGender(athleteDTO.gender());
        athleteSaved.setDateOfBirth(LocalDate.parse(athleteDTO.birthDate(), DataTimeFormatterUtils.FORMATTER_DATE));
        athleteSaved.setModality(athleteDTO.modality());
        athleteSaved.setMonitorName(athleteDTO.monitorName());
        athleteSaved.setBibNumber(athleteDTO.bibNumber());
        athleteSaved.setDocument(athleteDTO.document());
    }

    private AthleteDTO converterEntityToDto(final Athlete entity){
        return Objects.requireNonNull(converterEntityToDto.convert(entity));
    }

    private Athlete converterDtoToEntity(final AthleteDTO dto){
        return Objects.requireNonNull(converterDtoToEntity.convert(dto));
    }
}
