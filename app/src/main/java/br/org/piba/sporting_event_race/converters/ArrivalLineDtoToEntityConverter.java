package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.ArrivalLineDTO;
import br.org.piba.sporting_event_race.model.entity.ArrivalLine;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import br.org.piba.sporting_event_race.utils.RandomUUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class ArrivalLineDtoToEntityConverter implements Converter<ArrivalLineDTO, ArrivalLine> {
    @Override
    public ArrivalLine convert(ArrivalLineDTO source) {
        final UUID id = Objects.nonNull(source.id()) ? source.id() : RandomUUID.newUUID();
        return ArrivalLine.builder()
                .idUuid(id)
                .bibNumber(source.chesterNumber())
                .timeFinish(LocalTime.parse(source.hour(), DataTimeFormatterUtils.FORMATTER_HOUR))
                .monitor(source.monitorName())
                .build();
    }
}
