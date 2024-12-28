package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.model.dto.StartRaceDTO;
import br.org.piba.sporting_event_race.service.StartRaceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cronometragem/largadas")
public class StartRaceController {

    private final StartRaceService service;

    public StartRaceController(StartRaceService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StartRaceDTO>> get(@RequestParam(name = "monitor", required = false) final String monitor,
                                                @RequestParam(name = "numero_peito", required = false) final Integer bibNumber){
        return ResponseEntity.ok(service.getBy(monitor, bibNumber));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StartRaceDTO> save(@RequestBody StartRaceDTO startRaceDTO){
        final StartRaceDTO saved = service.save(startRaceDTO);
        URI uri = URI.create("/cronometragem/largadas/" + saved.id());
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping(path = "/{id_registro}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StartRaceDTO> update(@PathVariable("id_registro") final UUID id,
                                               @RequestBody final StartRaceDTO startRaceDTO){
        return ResponseEntity.ok(service.update(startRaceDTO, id));
    }

    @DeleteMapping(path = "/{id_registro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id_registro") final UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
