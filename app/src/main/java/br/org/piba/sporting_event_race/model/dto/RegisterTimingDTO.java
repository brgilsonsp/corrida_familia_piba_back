package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public record RegisterTimingDTO(@JsonProperty("operador") String operator,
                                @JsonProperty("numero_atleta") int athleteNumber,
                                @JsonProperty("momento_chegada") LocalTime arrivalTime) {

    @Override
    public String toString() {
        return "RegisterTimingDTO{" +
                "operator='" + operator + '\'' +
                ", athleteNumber=" + athleteNumber +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
