package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ClassificationDTO(@JsonProperty("posicao") int position,
                                @JsonProperty("nome_atleta") String athleteName,
                                @JsonProperty("tempo_corrida") String totalRaceTime,
                                @JsonProperty("idade") int age,
                                @JsonProperty("sexo") String gender,
                                @JsonProperty("numero_peito") int chesterNumber,
                                @JsonProperty("modalidade") String category,
                                @JsonProperty("monitor") String monitor) {
}
