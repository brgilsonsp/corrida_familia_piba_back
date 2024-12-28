package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.model.dto.StartRaceGeneralDTO;
import br.org.piba.sporting_event_race.service.StartRaceGeneralService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cronometragem/largada_geral")
public class StartRaceGeneralController {
    private final StartRaceGeneralService service;

    public StartRaceGeneralController(StartRaceGeneralService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StartRaceGeneralDTO> save(@RequestBody final StartRaceGeneralDTO dto){
        return ResponseEntity.ok(service.saveOrUpdate(dto));
    }
}
