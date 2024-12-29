package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SegmentDTO(@JsonProperty("sexo") List<String> gender,
                         @JsonProperty("range_idade") List<String> ageRange,
                         @JsonProperty("modalidade") List<String> modality) {
}
