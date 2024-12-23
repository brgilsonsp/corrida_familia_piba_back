package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.SegmentDTO;

@FunctionalInterface
public interface Segmentation {

    SegmentDTO getSegmentation();
}
