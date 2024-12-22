package br.org.piba.sporting_event_race.controller.mock;

import br.org.piba.sporting_event_race.model.dto.SegmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mock/segmentacao")
public class SegmentMockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SegmentMockController.class);
    private static final SegmentDTO segment;

    static {
        final List<String> gender = List.of("Masculino", "Feminino");
        final List<String> rangeAge = List.of("6-7", "8-12", "13-15", "16-20", "21-30", "21-40",
                "41-50", "61+");
        final List<String> category = List.of("Corrida", "Caminhada");
        segment = new SegmentDTO(gender, rangeAge, category);
    }

    @GetMapping
    public ResponseEntity<SegmentDTO> getSegments(){
        try{
            LOGGER.info("Success get segments {}", segment);
            return ResponseEntity.ok(segment);
        }catch (Exception e){
            LOGGER.error("Error get segment", e);
            throw e;
        }
    }
}
