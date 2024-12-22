package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.model.dto.ServerHourDTO;
import br.org.piba.sporting_event_race.service.HourServer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuracoes")
public class ConfigurationController {
    private final HourServer hourServer;

    public ConfigurationController(HourServer hourServer) {
        this.hourServer = hourServer;
    }

    @GetMapping(path = "/hora_servidor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerHourDTO> getHourServer(){
        return ResponseEntity.ok(hourServer.getHour());
    }

}
