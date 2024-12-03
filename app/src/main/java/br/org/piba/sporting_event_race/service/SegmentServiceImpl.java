package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.domain.Segment;
import br.org.piba.sporting_event_race.model.dto.SegmentDTO;
import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;
import br.org.piba.sporting_event_race.model.enumaration.RangeAgeDefinitionEnum;
import br.org.piba.sporting_event_race.repository.SegmentRepository;

import java.util.ArrayList;
import java.util.List;

public class SegmentServiceImpl implements SegmentService{

    private final SegmentRepository repository;

    public SegmentServiceImpl(SegmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public SegmentDTO getSegments(){
        final Segment segments = repository.getSegments();
        final List<String> ranges = getRanges(segments.rangeAge());
        final List<String> genders = getGenderEnum(segments.genderEnums());
        final List<String> categories = getCategoryEnum(segments.category());

        return new SegmentDTO(genders, ranges, categories);
    }

    private static List<String> getRanges(final List<RangeAgeDefinitionEnum> rangesEnum){
        final List<String> ranges = new ArrayList<>();
        for(RangeAgeDefinitionEnum range : rangesEnum){
            ranges.add(range.getValue());
        }
        return ranges;
    }

    private static List<String> getGenderEnum(final List<GenderEnum> gendersEnum){
        final List<String> genders = new ArrayList<>();
        for(GenderEnum gender : gendersEnum){
            genders.add(gender.getValue());
        }
        return genders;
    }

    private static List<String> getCategoryEnum(final List<CategoryEnum> categoriesEnum) {
        final List<String> categories = new ArrayList<>();
        for(CategoryEnum category : categoriesEnum){
            categories.add(category.getValue());
        }
        return categories;
    }
}
