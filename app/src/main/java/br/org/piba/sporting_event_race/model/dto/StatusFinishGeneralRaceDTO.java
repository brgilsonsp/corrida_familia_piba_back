package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatusFinishGeneralRaceDTO (@JsonProperty("monitor") String monitorName) {
}
