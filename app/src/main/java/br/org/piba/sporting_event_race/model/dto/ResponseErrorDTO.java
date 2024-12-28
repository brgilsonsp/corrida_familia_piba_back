package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ResponseErrorDTO(String message, String code) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
    @JsonProperty("timestamp")
    public String timestamp(){
        return LocalDateTime.now().format(FORMATTER);
    }
}
