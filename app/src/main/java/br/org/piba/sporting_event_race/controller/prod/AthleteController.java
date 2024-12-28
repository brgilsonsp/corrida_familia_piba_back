package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.exception.NoContentException;
import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.service.AthleteService;
import br.org.piba.sporting_event_race.utils.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/atletas")
public class AthleteController {

    public static final String NO_CONTENT = "Registros não encontrado. Documento: [{0}], Nome Parcial: [{1}]";
    private final AthleteService service;

    public AthleteController(AthleteService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AthleteDTO>> getAthlete(@RequestParam(name = "documento", required = false) String document,
                                                       @RequestParam(name = "nome_parcial", required = false) String partialName){
        final List<AthleteDTO> athlete = service.getAthleteByDocumentAndPartialName(document, partialName);
        if(CollectionUtils.isNullOrEmpty(athlete)){
            final String message = MessageFormat.format(NO_CONTENT, document, partialName);
            throw new NoContentException(message);
        }
        return ResponseEntity.ok(athlete);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AthleteDTO> save(@RequestBody AthleteDTO athleteDTO){
        final AthleteDTO athleteSaved = service.saveAthlete(athleteDTO);
        URI uri = URI.create("/atletas/" + athleteSaved.id());
        return ResponseEntity.created(uri).body(athleteSaved);
    }

    @DeleteMapping(path = "/{id_atleta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void>  delete(@PathVariable("id_atleta") UUID idAthlete){
        service.deleteAthlete(idAthlete);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id_atleta}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AthleteDTO> update(@PathVariable("id_atleta") UUID idAthlete,
                                             @RequestBody AthleteDTO athleteDTO){
        return ResponseEntity.ok(service.updateAthlete(athleteDTO, idAthlete));
    }
}
