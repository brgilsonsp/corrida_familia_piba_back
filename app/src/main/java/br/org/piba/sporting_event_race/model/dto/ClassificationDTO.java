package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;

public record ClassificationDTO(@JsonProperty("posicao") int position,
                                @JsonProperty("nome_completo_atleta") String athleteFullName,
                                @JsonProperty("tempo_corrida") String totalRaceTime,
                                @JsonProperty("idade") int age,
                                @JsonProperty("sexo") String gender,
                                @JsonProperty("numero_atleta") int number_athlete,
                                @JsonProperty("categoria") String category,
                                @JsonProperty("operador") String timerOperator) {
}
