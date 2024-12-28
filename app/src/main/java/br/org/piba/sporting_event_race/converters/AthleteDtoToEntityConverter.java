package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.entity.Athlete;
import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import br.org.piba.sporting_event_race.utils.RandomUUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
public class AthleteDtoToEntityConverter implements Converter<AthleteDTO, Athlete> {

    @Override
    public Athlete convert(AthleteDTO source) {
        final UUID id = Objects.nonNull(source.id()) ?
                source.id() : RandomUUID.newUUID();
        return Athlete.builder()
                .idUuid(id)
                .document(source.document())
                .name(source.name())
                .gender(source.gender())
                .dateOfBirth(LocalDate.parse(source.birthDate(), DataTimeFormatterUtils.FORMATTER_DATE))
                .modality(source.modality())
                .bibNumber(source.chesterNumber())
                .build();
    }
}
