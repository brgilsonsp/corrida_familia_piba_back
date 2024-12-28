package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.StartRaceGeneralDTO;
import br.org.piba.sporting_event_race.model.entity.StartRaceGeneral;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StartRaceGeneralEntityToDtoConverter implements Converter<StartRaceGeneral, StartRaceGeneralDTO> {

    @Override
    public StartRaceGeneralDTO convert(StartRaceGeneral source) {
        return new StartRaceGeneralDTO(source.getIdUuid(),
                source.getTimeStart().format(DataTimeFormatterUtils.FORMATTER_HOUR),
                source.getMonitor());
    }
}
