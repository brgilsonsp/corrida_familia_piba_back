package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ResponseErrorDTO(String message, String code) {

    @JsonProperty("timestamp")
    public LocalDateTime timestamp(){
        return LocalDateTime.now();
    }
}
