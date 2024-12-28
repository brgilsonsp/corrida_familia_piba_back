package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AthleteDTO(@JsonProperty("id_atleta") UUID id,
                         @JsonProperty("nome") String name,
                         @JsonProperty("documento") String document,
                         @JsonProperty("numero_peito") Integer chesterNumber,
                         @JsonProperty("sexo") String gender,
                         @JsonProperty("data_nascimento") String birthDate,
                         @JsonProperty("modalidade") String modality,
                         @JsonProperty("monitor") String monitorName) {
}
