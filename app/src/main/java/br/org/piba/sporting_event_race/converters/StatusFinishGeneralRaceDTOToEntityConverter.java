package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.StatusFinishGeneralRaceDTO;
import br.org.piba.sporting_event_race.model.entity.StatusFinishGeneralRace;
import br.org.piba.sporting_event_race.service.impl.StatusFinishGeneralRaceServiceImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class StatusFinishGeneralRaceDTOToEntityConverter implements Converter<StatusFinishGeneralRaceDTO, StatusFinishGeneralRace> {

    @Override
    public StatusFinishGeneralRace convert(StatusFinishGeneralRaceDTO source) {
        return StatusFinishGeneralRace.builder()
                .timeFinishGeneral(LocalTime.now())
                .statusGeneralRace(StatusFinishGeneralRaceServiceImpl.RACE_CLOSED)
                .monitorName(source.monitorName())
                .build();
    }
}
