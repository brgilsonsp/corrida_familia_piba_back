package br.org.piba.sporting_event_race.controller.mock;

import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/mock/classificacao")
public class ClassificationAthletesMockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationAthletesMockController.class);
    private static final List<ClassificationDTO> listClassification;

    static{
        ClassificationDTO ana = new ClassificationDTO(1, "Ana Cristina",
                "00:32:34.234", 25, "Feminino", 12345,
                "Corrida", "Andre");

        ClassificationDTO pedro = new ClassificationDTO(2, "Pedro",
                "00:32:54.768", 28, "Masculino", 4321,
                "Corrida", "Flavia");

        ClassificationDTO felipe = new ClassificationDTO(1, "Felipe",
                "00:52:34.234", 45, "Masculino", 9876,
                "Caminhada", "Paula");

        listClassification = new ArrayList<>();
        listClassification.add(ana);
        listClassification.add(pedro);
        listClassification.add(felipe);
    }

    @GetMapping
    public ResponseEntity<?> getClassificationsByGender(@RequestParam(name="sexo", required = false) final String gender,
                                                         @RequestParam(name="faixa_etaria", required = false) final String ageRange){
        try{
            List<ClassificationDTO> filtered = listClassification.stream()
                    .filter(s -> {
                        if (Objects.nonNull(gender) && !gender.isBlank()) {
                            return s.gender().equalsIgnoreCase(gender);
                        }
                        return true;
                    })
                    .filter(s -> {
                        if (Objects.nonNull(ageRange) && !ageRange.isBlank()) {
                            String[] splitRange = ageRange.split("-");
                            final int startRange = splitRange.length > 1 ?
                                    Integer.parseInt(splitRange[0].trim()) : Integer.parseInt(ageRange.substring(0, 1));
                            final int endRange = splitRange.length > 1 ?
                                    Integer.parseInt(splitRange[1]) : 300;
                            return s.age() >= startRange && s.age() <= endRange;
                        }
                        return true;
                    }).toList();

            if(filtered.isEmpty()){
                LOGGER.info("Classification not found by gender: {} and age_range: {}",
                        gender, ageRange);
                return ResponseUtils.buildRegisterNotFound204();
            }else{
                LOGGER.info("Get classification by gender: {} and age_range: {}",
                        gender, ageRange);
                return ResponseEntity.ok(filtered);
            }
        }catch (Exception e){
            LOGGER.error("Error get classification", e);
            throw e;
        }

    }
}
