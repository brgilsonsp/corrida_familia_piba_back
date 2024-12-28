package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.StartRaceGeneralDTO;
import br.org.piba.sporting_event_race.model.entity.StartRaceGeneral;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import br.org.piba.sporting_event_race.utils.RandomUUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class StartRaceGeneralDtoToEntityConverter implements Converter<StartRaceGeneralDTO, StartRaceGeneral> {

    @Override
    public StartRaceGeneral convert(StartRaceGeneralDTO source) {
        final UUID id = Objects.nonNull(source.id()) ? source.id() : RandomUUID.newUUID();
        return StartRaceGeneral.builder()
                .idUuid(id)
                .timeStart(LocalTime.parse(source.arrivalTime(), DataTimeFormatterUtils.FORMATTER_HOUR))
                .monitor(source.monitorName())
                .build();
    }
}
