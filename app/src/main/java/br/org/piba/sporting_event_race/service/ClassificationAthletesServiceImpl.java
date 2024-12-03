package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.domain.AgeRange;
import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;
import br.org.piba.sporting_event_race.model.enumaration.RangeAgeDefinitionEnum;
import br.org.piba.sporting_event_race.model.enumaration.StatusTimerEnum;
import br.org.piba.sporting_event_race.repository.AthleteDataRepository;
import br.org.piba.sporting_event_race.repository.SegmentRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClassificationAthletesServiceImpl implements ClassificationAthletesService{
    private final AthleteDataRepository athleteRepository;
    private final ClassificationDefinitionService classificationDefinitionService;
    private final SegmentRepository segmentRepository;

    public ClassificationAthletesServiceImpl(AthleteDataRepository athleteRepository,
                                             ClassificationDefinitionService classificationDefinitionService,
                                             SegmentRepository segmentRepository) {
        this.athleteRepository = athleteRepository;
        this.classificationDefinitionService = classificationDefinitionService;
        this.segmentRepository = segmentRepository;
    }

    @Override
    public List<ClassificationDTO> getClassificationBy(final String gender,
                                                       final String ageRange,
                                                       final String category) {
        final GenderEnum genderEnum = getGenderEnum(gender);
        final CategoryEnum categoryEnum = getCategoryEnum(category);
        final AgeRange ageRangeObject = getAgeRange(ageRange);
        final List<AthleteData> allAthletes = athleteRepository
                .getListBy(ageRangeObject.initialAge(), ageRangeObject.limitAge(), genderEnum, categoryEnum);
        return getClassificationDTOS(allAthletes);
    }

    private static GenderEnum getGenderEnum(final String gender) {
        return Objects.isNull(gender) || gender.isEmpty()
                ? GenderEnum.ALL : GenderEnum.getEnum(gender.trim());
    }

    private static CategoryEnum getCategoryEnum(final String category) {
        return Objects.isNull(category) || category.isEmpty()
                ? CategoryEnum.ALL : CategoryEnum.getEnum(category.trim());
    }

    private AgeRange getAgeRange(final String ageRange) {
        final RangeAgeDefinitionEnum ageDefinitionEnum = Objects.isNull(ageRange) || ageRange.isEmpty()
                ? RangeAgeDefinitionEnum.ALL : RangeAgeDefinitionEnum.getEnum(ageRange.trim());
        return segmentRepository.getAgeRangeBy(ageDefinitionEnum);
    }

    private List<ClassificationDTO> getClassificationDTOS(List<AthleteData> allAthletes) {
        Map<StatusTimerEnum, List<AthleteData>> listSegregated = classificationDefinitionService.segregateListAthletes(allAthletes);
        return classificationDefinitionService.makeClassification(listSegregated);
    }

}
