package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseDTO(@JsonProperty("codigo") String code,
                               @JsonProperty("mensagem") String message) {
}
