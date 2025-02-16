package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.converters.AthleteTimerToClassificationConverter;
import br.org.piba.sporting_event_race.model.bo.FilterClassificationBO;
import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.dto.StatusFinishGeneralRaceDTO;
import br.org.piba.sporting_event_race.model.entity.Classification;
import br.org.piba.sporting_event_race.model.enums.GenderEnum;
import br.org.piba.sporting_event_race.repository.ClassificationRepository;
import br.org.piba.sporting_event_race.service.AthleteService;
import br.org.piba.sporting_event_race.service.ClassificationService;
import br.org.piba.sporting_event_race.service.StatusFinishGeneralRaceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class ClassificationServiceImpl implements ClassificationService {
    private final StatusFinishGeneralRaceService statusRaceService;
    private final AthleteService athleteService;
    private final AthleteTimerToClassificationConverter athleteTimerToClassificationDto;
    private final ClassificationRepository repository;

    public ClassificationServiceImpl(StatusFinishGeneralRaceService statusRaceService,
                                     AthleteService athleteService,
                                     AthleteTimerToClassificationConverter athleteTimerToClassificationDto,
                                     ClassificationRepository repository) {
        this.statusRaceService = statusRaceService;
        this.athleteService = athleteService;
        this.athleteTimerToClassificationDto = athleteTimerToClassificationDto;
        this.repository = repository;
    }

    @Override
    public List<ClassificationDTO> getClassification(final FilterClassificationBO filterClassification) {
        if(raceNotClosed()){
            this.closeRace(new StatusFinishGeneralRaceDTO("AUTOMATIC"));
        }
        final List<Classification> classificationsFound = GenderEnum.ALL.equals(filterClassification.getGender()) ?
                repository.findByAgeBetween(filterClassification.getInitialAge(), filterClassification.getEndAge()) :
                repository.findByGenderAndAgeBetween(filterClassification.getGender().getValue(), filterClassification.getInitialAge(), filterClassification.getEndAge());
        return getClassificationDTOS(classificationsFound);
    }

    @Transactional
    @Override
    public void closeRace(StatusFinishGeneralRaceDTO statusFinishGeneralRaceDTO) {
        repository.deleteAll();
        statusRaceService.closeRace(statusFinishGeneralRaceDTO);
        final List<Classification> classifications = athleteService.getAthleteTimer().stream()
                .filter(Objects::nonNull)
                .map(athleteTimerToClassificationDto::convert)
                .toList();
        repository.saveAllAndFlush(classifications);
    }

    private static List<ClassificationDTO> getClassificationDTOS(List<Classification> classificationsSaved) {
        final List<Classification> collect = extractPositiveAndSortedClassification(classificationsSaved);
        final List<ClassificationDTO> classificationDTOS = new ArrayList<>();
        for(int i = 0; i<= collect.size()-1; i++){
            Classification classification = collect.get(i);
            final ClassificationDTO classificationDTO = new ClassificationDTO(i + 1, classification.getAthleteName(), classification.getTotalTime(),
                    classification.getAge(), classification.getGender(), classification.getBibNumber(),
                    classification.getModality(), classification.getMonitorNameFinishRace());
            classificationDTOS.add(classificationDTO);
        }
        return classificationDTOS;
    }

    private static List<Classification> extractPositiveAndSortedClassification(List<Classification> classificationsSaved) {
        return classificationsSaved.stream()
                .filter(a -> a.getTotalTime().isAfter(LocalTime.MIN))
                .sorted(Comparator.comparing(Classification::getTotalTime))
                .toList();
    }

    private boolean raceNotClosed() {
        return !statusRaceService.raceClosed();
    }
}
