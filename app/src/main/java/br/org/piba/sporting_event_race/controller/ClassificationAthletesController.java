package br.org.piba.sporting_event_race.controller;

import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.dto.ListDataDTO;
import br.org.piba.sporting_event_race.service.ClassificationAthletesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classificacoes")
public class ClassificationAthletesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationAthletesController.class);

    private final ClassificationAthletesService classificationService;

    public ClassificationAthletesController(ClassificationAthletesService classificationService) {
        this.classificationService = classificationService;
    }

    @GetMapping
    public ResponseEntity<ListDataDTO<ClassificationDTO>> getClassificationsByGender(@RequestParam(name="sexo", required = false) final String gender,
                                                                                     @RequestParam(name="faixa_etaria", required = false) final String ageRange,
                                                                                     @RequestParam(name="categoria", required = false) final String category){
        LOGGER.info("Obtendo lista de classificação para : sexo: {}, faixa_etaria: {}, categoria{}", gender, ageRange, category);
        final List<ClassificationDTO> classifications = classificationService.getClassificationBy(gender, ageRange, category);
        return ResponseEntity.ok(new ListDataDTO<>(classifications));
    }
}
