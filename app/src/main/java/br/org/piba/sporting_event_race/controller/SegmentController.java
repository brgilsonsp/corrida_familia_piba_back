package br.org.piba.sporting_event_race.controller;

import br.org.piba.sporting_event_race.model.dto.SegmentDTO;
import br.org.piba.sporting_event_race.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/segmentos")
public class SegmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SegmentController.class);
    private final SegmentService service;

    public SegmentController(SegmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SegmentDTO> getSegments(){
        LOGGER.info("Obtendo lista de sementos}");
        return ResponseEntity.ok(service.getSegments());
    }
}
