package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.FinishRaceDTO;
import br.org.piba.sporting_event_race.model.entity.FinishRace;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import br.org.piba.sporting_event_race.utils.RandomUUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class ArrivalLineDtoToEntityConverter implements Converter<FinishRaceDTO, FinishRace> {
    @Override
    public FinishRace convert(FinishRaceDTO source) {
        final UUID id = Objects.nonNull(source.id()) ? source.id() : RandomUUID.newUUID();
        return FinishRace.builder()
                .idUuid(id)
                .bibNumber(source.bibNumber())
                .timeFinish(LocalTime.parse(source.hour(), DataTimeFormatterUtils.FORMATTER_HOUR))
                .monitorName(source.monitorName())
                .build();
    }
}
