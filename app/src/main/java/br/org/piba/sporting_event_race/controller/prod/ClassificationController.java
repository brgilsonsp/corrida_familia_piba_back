package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.exception.NoContentException;
import br.org.piba.sporting_event_race.model.bo.FilterClassificationBO;
import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.dto.StatusFinishGeneralRaceDTO;
import br.org.piba.sporting_event_race.service.ClassificationService;
import br.org.piba.sporting_event_race.service.FilterClassificationBuilder;
import br.org.piba.sporting_event_race.utils.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/classificacao")
public class ClassificationController {

    private final FilterClassificationBuilder builderFilter;
    private final ClassificationService classificationService;

    public ClassificationController(FilterClassificationBuilder builderFilter, ClassificationService classificationService) {
        this.builderFilter = builderFilter;
        this.classificationService = classificationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClassificationDTO>> getClassification(@RequestParam(name = "faixa_etaria", required = false) final String ageRange,
                                                                     @RequestParam(name = "sexo", required = false) final String gender) {
        final FilterClassificationBO filterClassification = builderFilter.builder(ageRange, gender);
        final List<ClassificationDTO> classification = classificationService.getClassification(filterClassification);
        if(CollectionUtils.isNullOrEmpty(classification)){
            final String message = MessageFormat.format("Registros não encontrado. Faixa etária: {0}, sexo: {1}",
                    ageRange, gender);
            throw new NoContentException(message);
        }
        return ResponseEntity.ok(classification);
    }

    @PostMapping(value = "/encerrar_corrida", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> closeRace(@RequestBody final StatusFinishGeneralRaceDTO statusFinishGeneralRaceDTO){
        classificationService.closeRace(statusFinishGeneralRaceDTO);
        return ResponseEntity.ok().build();
    }
}
