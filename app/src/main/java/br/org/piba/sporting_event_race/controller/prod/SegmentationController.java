package br.org.piba.sporting_event_race.controller.prod;

import br.org.piba.sporting_event_race.model.dto.SegmentDTO;
import br.org.piba.sporting_event_race.service.Segmentation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/segmentacao")
public class SegmentationController {

    private final Segmentation segmentation;

    public SegmentationController(Segmentation segmentation) {
        this.segmentation = segmentation;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SegmentDTO> getSegmentation(){
        final SegmentDTO segmentDTO = this.segmentation.getSegmentation();
        return ResponseEntity.ok(segmentDTO);
    }
}
