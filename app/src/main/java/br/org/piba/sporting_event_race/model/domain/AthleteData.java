package br.org.piba.sporting_event_race.model.domain;

import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class AthleteData {

    private final UUID id;
    private final String name;
    private final GenderEnum genderEnum;
    private final LocalDate birthDate;
    private final CategoryEnum category;
    private int number;
    private LocalDateTime startTime;
    private LocalDateTime arrivalTime;
    private String operator;

    public AthleteData(String name, GenderEnum genderEnum, LocalDate birthDate, CategoryEnum category) {
        this.name = name;
        this.genderEnum = genderEnum;
        this.birthDate = birthDate;
        this.category = category;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GenderEnum getGender() {
        return genderEnum;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration totalRaceTime(){
        if(Objects.isNull(arrivalTime) || Objects.isNull(startTime)){
            return Duration.ZERO;
        }
        return Duration.between(startTime, arrivalTime);
    }

    public String getFormattedTotalRaceTime() {
        final Duration duration = totalRaceTime();
        final long hours = duration.toHours();
        final long minutes = duration.toMinutesPart();
        final long seconds = duration.toSecondsPart();
        final long nanos = duration.toNanosPart();
        return String.format("%02d:%02d:%02d.%09d", hours, minutes, seconds, nanos);
    }

    public int calculateAge(){
        final LocalDate birth = this.getBirthDate();
        if(Objects.isNull(birth)){
            return 0;
        }
        final int yearNow = LocalDate.now().getYear();
        return yearNow - birth.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AthleteData that = (AthleteData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AthleteData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + genderEnum +
                ", birthDate=" + birthDate +
                ", number=" + number +
                ", startTime=" + startTime +
                ", arrivalTime=" + arrivalTime +
                ", operator='" + operator + '\'' +
                '}';
    }
}
