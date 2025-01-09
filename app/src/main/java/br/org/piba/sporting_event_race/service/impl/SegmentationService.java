package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.model.dto.SegmentDTO;
import br.org.piba.sporting_event_race.service.Segmentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentationService implements Segmentation {

    private static final SegmentDTO SEGMENT;

    static {
        final List<String> gender = List.of("Masculino", "Feminino");
        final List<String> rangeAge = List.of("6-7",
                "8-12",
                "13-15",
                "16-29",
                "30-39",
                "40-49",
                "50-59",
                "61+");
        final List<String> category = List.of("Corrida", "Caminhada");
        SEGMENT = new SegmentDTO(gender, rangeAge, category);
    }

    @Override
    public SegmentDTO getSegmentation() {
        return SEGMENT;
    }
}
