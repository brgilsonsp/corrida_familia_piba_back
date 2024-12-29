package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StartRaceDTO(@JsonProperty("id_largada") UUID id,
                           @JsonProperty("numero_peito") Integer bibNumber,
                           @JsonProperty("hora") String arrivalTime,
                           @JsonProperty("monitor") String monitorName,
                           @JsonProperty("nome_atleta") String athleteName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartRaceDTO that = (StartRaceDTO) o;
        return Objects.equals(arrivalTime, that.arrivalTime) &&
                Objects.equals(monitorName, that.monitorName) &&
                Objects.equals(bibNumber, that.bibNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bibNumber, arrivalTime, monitorName);
    }
}
