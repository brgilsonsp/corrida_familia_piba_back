package br.org.piba.sporting_event_race.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ListDataDTO<T> (@JsonProperty("dados") List<T> data){
}
