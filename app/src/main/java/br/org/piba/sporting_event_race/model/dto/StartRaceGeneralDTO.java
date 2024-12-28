package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StartRaceGeneralDTO(@JsonProperty("id_largada") UUID id,
                                  @JsonProperty("hora") String arrivalTime,
                                  @JsonProperty("monitor") String monitorName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartRaceGeneralDTO that = (StartRaceGeneralDTO) o;
        return Objects.equals(arrivalTime, that.arrivalTime) &&
                Objects.equals(monitorName, that.monitorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalTime, monitorName);
    }
}
