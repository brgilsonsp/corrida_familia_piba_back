package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AthleteFullNameRegisterDTO(@JsonProperty("id") UUID id,
                                         @JsonProperty("nome_completo") String names) {
}
