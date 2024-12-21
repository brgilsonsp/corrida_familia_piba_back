package br.org.piba.sporting_event_race.controller;

import br.org.piba.sporting_event_race.model.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class ResponseUtils {

    public static ErrorResponseDTO buildError(final String message){
        return new ErrorResponseDTO(UUID.randomUUID().toString(), message);
    }

    public static ResponseEntity<ErrorResponseDTO> buildRegisterNotFound204(){
        return ResponseEntity.status(204).body(ResponseUtils.buildError("Nenhum registro encontrado"));
    }

    public static ResponseEntity<ErrorResponseDTO> buildRegisterNotFound404(){
        return ResponseEntity.status(404).body(ResponseUtils.buildError("Registro não encontrado"));
    }

    public static ResponseEntity<ErrorResponseDTO> buildError500(){
        return ResponseEntity.status(500).body(ResponseUtils.buildError("Erro ao atualizar registro"));
    }
}
