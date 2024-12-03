package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RegistersTimingDTO(@JsonProperty("dados") List<RegisterTimingDTO> registersTiming) {

    @Override
    public String toString() {
        return "RegistersTimingDTO{" +
                "registersTiming=" + registersTiming +
                '}';
    }
}
