package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArrivalLineDTO(@JsonProperty("id_cronometragem") UUID id,
                             @JsonProperty("numero_peito") Integer chesterNumber,
                             @JsonProperty("hora") String hour,
                             @JsonProperty("monitor") String monitorName,
                             @JsonProperty("nome_atleta") String athleteName) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrivalLineDTO that = (ArrivalLineDTO) o;
        return Objects.equals(hour, that.hour) &&
                Objects.equals(monitorName, that.monitorName) &&
                Objects.equals(chesterNumber, that.chesterNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chesterNumber, hour, monitorName);
    }
}
