package br.org.piba.sporting_event_race.controller;

import br.org.piba.sporting_event_race.model.dto.ServerHourDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/configuracoes")
public class ConfigurationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationController.class);

    @GetMapping(path = "/hora_servidor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerHourDTO> getHourServer(){

        try{
            final String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            LOGGER.info("Get hour server:{}", hour);
            return ResponseEntity.ok(new ServerHourDTO(hour));
        }catch (Exception e){
            LOGGER.info("Error get hour server", e);
            throw e;
        }

    }
}
