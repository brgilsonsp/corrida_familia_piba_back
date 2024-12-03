package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.domain.AgeRange;
import br.org.piba.sporting_event_race.model.domain.Segment;
import br.org.piba.sporting_event_race.model.enumaration.RangeAgeDefinitionEnum;

public interface SegmentRepository {

    Segment getSegments();

    AgeRange getAgeRangeBy(RangeAgeDefinitionEnum rangeAgeDefinition);
}
