package br.org.piba.sporting_event_race.model.dto;

import br.org.piba.sporting_event_race.utils.DataTimeFormatterUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalTime;


public record ClassificationDTO(@JsonProperty("posicao") int position,
                                @JsonProperty("nome_atleta") String athleteName,
                                @JsonIgnore LocalTime totalRaceTime,
                                @JsonProperty("idade") int age,
                                @JsonProperty("sexo") String gender,
                                @JsonProperty("numero_peito") int bibNumber,
                                @JsonProperty("modalidade") String modality,
                                @JsonProperty("monitor") String monitor) {
    @JsonProperty("tempo_corrida")
    public String getTotalRace(){
        return totalRaceTime.format(DataTimeFormatterUtils.FORMATTER_HOUR);
    }
}
