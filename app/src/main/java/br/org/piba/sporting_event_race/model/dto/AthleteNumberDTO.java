package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AthleteNumberDTO(@JsonProperty("id") UUID id,
                               @JsonProperty("numero_atleta") int number) {

    @Override
    public String toString() {
        return "AthleteNumberDTO{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}
