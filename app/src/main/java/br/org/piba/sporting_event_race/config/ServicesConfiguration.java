package br.org.piba.sporting_event_race.config;

import br.org.piba.sporting_event_race.repository.AthleteDataMockRepository;
import br.org.piba.sporting_event_race.repository.AthleteDataRepository;
import br.org.piba.sporting_event_race.repository.SegmentMockRepository;
import br.org.piba.sporting_event_race.repository.SegmentRepository;
import br.org.piba.sporting_event_race.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ServicesConfiguration {
    public static final LocalDateTime START_TIME = LocalDateTime.now()
            .minusHours(1).withMinute(0).withSecond(0).withNano(0);

    @Bean
    public AthleteDataRepository getAthleteDataRepository(){
        return new AthleteDataMockRepository();
    }

    @Bean
    public SegmentRepository getAgeRangeDataBase(){
        return new SegmentMockRepository();
    }

    @Bean
    public StartRaceService getStartRaceService(){
        return new StartRaceServiceMock(START_TIME);
    }

    @Bean
    public TimingAthleteService getTimingAthleteService(AthleteDataRepository repository,
                                                        StartRaceService startRaceService){
        return new TimingAthleteServiceImpl(repository, startRaceService);
    }

    @Bean
    public AthleteService getAthleteService(AthleteDataRepository repository){
        return new AthleteServiceImpl(repository);
    }

    @Bean
    public SegmentService getSegmentService(SegmentRepository repository){
        return new SegmentServiceImpl(repository);
    }

    @Bean
    public ClassificationDefinitionService getClassificationDefinitionService(){
        return new ClassificationDefinitionServiceImpl();
    }

    @Bean
    public ClassificationAthletesService getClassificationAthletesService(AthleteDataRepository athleteRepository,
                                                                          ClassificationDefinitionService classificationDefinitionService,
                                                                          SegmentRepository repository){
        return new ClassificationAthletesServiceImpl(athleteRepository, classificationDefinitionService, repository);
    }

}
