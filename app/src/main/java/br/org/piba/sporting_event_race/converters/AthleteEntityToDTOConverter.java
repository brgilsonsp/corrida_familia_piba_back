package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.entity.Athlete;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AthleteEntityToDTOConverter implements Converter<Athlete, AthleteDTO> {


    @Override
    public AthleteDTO convert(Athlete source) {
        return new AthleteDTO(source.getIdUuid(), source.getName(),
                source.getDocument(), source.getBibNumber(), source.getGender(),
                source.getDateOfBirth().format(DataTimeFormatterUtils.FORMATTER_DATE),
                source.getModality(), source.getMonitor());
    }
}
