package br.org.piba.sporting_event_race.converters;

import br.org.piba.sporting_event_race.model.dto.AthleteTimerDTO;
import br.org.piba.sporting_event_race.model.entity.Classification;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class AthleteTimerToClassificationConverter implements Converter<AthleteTimerDTO, Classification> {
    @Override
    public Classification convert(AthleteTimerDTO source) {
        return Classification.builder()
                .bibNumber(source.bibNumber())
                .athleteName(source.athleteName())
                .age(source.getAge())
                .gender(source.gender())
                .modality(source.modality())
                .totalTime(source.getTotalRace())
                .monitorNameFinishRace(source.monitorNameFinishRace())
                .build();
    }
}
