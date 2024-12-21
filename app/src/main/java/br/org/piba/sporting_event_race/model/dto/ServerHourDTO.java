package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServerHourDTO (@JsonProperty("hora") String hour) {
}
