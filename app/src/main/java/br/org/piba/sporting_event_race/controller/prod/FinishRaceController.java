package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.exception.NoContentException;
import br.org.piba.sporting_event_race.model.dto.FinishRaceDTO;
import br.org.piba.sporting_event_race.service.FinishRaceService;
import br.org.piba.sporting_event_race.utils.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cronometragem/chegadas")
public class FinishRaceController {
    private final FinishRaceService service;

    public FinishRaceController(FinishRaceService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinishRaceDTO> save(@RequestBody final FinishRaceDTO dto){
        FinishRaceDTO saved = service.save(dto);
        URI uri = URI.create("/cronometragem/chegadas/" + saved.id());
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FinishRaceDTO>> get(@RequestParam(name = "monitor", required = false) final String monitor,
                                                   @RequestParam(name = "numero_peito", required = false) final Integer bibNumber){
        List<FinishRaceDTO> listFound = service.getBy(monitor, bibNumber);
        if(CollectionUtils.isNullOrEmpty(listFound)){
            throw new NoContentException("Reigstros não encontrado");
        }
        return ResponseEntity.ok(listFound);
    }

    @PutMapping(path = "/{id_registro}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinishRaceDTO> update(@PathVariable("id_registro") final UUID idRegister,
                                                @RequestBody final FinishRaceDTO dto){
        return ResponseEntity.ok(service.update(dto, idRegister));
    }

    @DeleteMapping(path = "/{id_registro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id_registro") final UUID idRegister){
        service.delete(idRegister);
        return ResponseEntity.noContent().build();
    }
}
