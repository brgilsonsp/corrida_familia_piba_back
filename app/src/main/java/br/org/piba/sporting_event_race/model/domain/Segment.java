package br.org.piba.sporting_event_race.model.domain;

import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;
import br.org.piba.sporting_event_race.model.enumaration.RangeAgeDefinitionEnum;

import java.util.List;

public record Segment(List<GenderEnum> genderEnums,
                      List<RangeAgeDefinitionEnum> rangeAge,
                      List<CategoryEnum> category) {
}
