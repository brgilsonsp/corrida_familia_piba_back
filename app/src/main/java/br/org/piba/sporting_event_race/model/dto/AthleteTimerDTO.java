package br.org.piba.sporting_event_race.model.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public record AthleteTimerDTO(Integer bibNumber,
                              String athleteName,
                              String gender,
                              LocalDate dateOfBirth,
                              String modality,
                              LocalTime timeStart,
                              LocalTime timeFinish,
                              String monitorNameFinishRace) {

    public int getAge(){
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public LocalTime getTotalRace(){
        Duration between = Duration.between(timeStart, timeFinish);
        return between.isNegative() ?
                LocalTime.MIN :
                LocalTime.of(between.toHoursPart(), between.toMinutesPart(), between.toSecondsPart(), between.toNanosPart());
    }

}
