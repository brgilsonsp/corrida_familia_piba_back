package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.infra.AgeRangeDataBase;
import br.org.piba.sporting_event_race.infra.CategoryDataBase;
import br.org.piba.sporting_event_race.infra.GenderDataBase;
import br.org.piba.sporting_event_race.model.domain.AgeRange;
import br.org.piba.sporting_event_race.model.domain.Segment;
import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;
import br.org.piba.sporting_event_race.model.enumaration.RangeAgeDefinitionEnum;

import java.util.List;
import java.util.Map;

public class SegmentMockRepository implements SegmentRepository{
    private final List<GenderEnum> GENDERS;
    private final List<RangeAgeDefinitionEnum> RANGES;
    private final Map<RangeAgeDefinitionEnum, AgeRange> AGE_RANGES;
    private final List<CategoryEnum> CATEGORY;

    public SegmentMockRepository() {
        GENDERS = GenderDataBase.GENDER_ENUMS;
        RANGES = AgeRangeDataBase.RANGES;
        AGE_RANGES = AgeRangeDataBase.AGE_RANGES;
        CATEGORY = CategoryDataBase.CATEGORIES;
    }

    @Override
    public Segment getSegments(){
        return new Segment(GENDERS, RANGES, CATEGORY);
    }

    @Override
    public AgeRange getAgeRangeBy(final RangeAgeDefinitionEnum rangeAgeDefinition) {
        return AGE_RANGES.get(rangeAgeDefinition);
    }
}
