package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.StartRaceDTO;
import br.org.piba.sporting_event_race.model.entity.StartRace;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import br.org.piba.sporting_event_race.utils.RandomUUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class StartRaceDtoToEntityConverter implements Converter<StartRaceDTO, StartRace> {
    @Override
    public StartRace convert(StartRaceDTO source) {
        final UUID id = Objects.nonNull(source.id()) ?
                source.id() : RandomUUID.newUUID();
        return StartRace.builder()
                .idUuid(id)
                .bibNumber(source.bibNumber())
                .timeStart(LocalTime.parse(source.arrivalTime(), DataTimeFormatterUtils.FORMATTER_HOUR))
                .monitor(source.monitorName())
                .build();
    }
}
