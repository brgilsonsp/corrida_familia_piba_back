package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.infra.AthleteDataBase;
import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;

import java.time.LocalDate;
import java.util.*;

public class AthleteDataMockRepository implements AthleteDataRepository {

    private final Map<UUID, AthleteData> ATHLETES;

    public AthleteDataMockRepository() {
        ATHLETES = new AthleteDataBase().getAthletes();
    }

    @Override
    public Optional<AthleteData> getAthleteByNumber(final int number){
        return ATHLETES.values().stream()
                .filter(athlete -> athlete.getNumber() == number)
                .findFirst();
    }

    @Override
    public Optional<AthleteData> getAthleteById(final UUID id){
        return Optional.ofNullable(ATHLETES.get(id));
    }

    @Override
    public Optional<AthleteData> update(final AthleteData athlete){
        return Optional.ofNullable(ATHLETES.replace(athlete.getId(), athlete));
    }

    @Override
    public List<AthleteData> getListBy(final String startName){
        return ATHLETES.values().stream()
                .filter(athlete -> athlete.getName().toLowerCase().startsWith(startName.toLowerCase()))
                .toList();
    }

    @Override
    public List<AthleteData> getListBy(final GenderEnum genderEnum){
        if(Objects.isNull(genderEnum)){
            return List.of();
        }

        return ATHLETES.values().stream()
                .filter(athlete -> genderEnum.equals(athlete.getGender()))
                .toList();
    }

    @Override
    public List<AthleteData> getListBy(final int minAge, final int maxAge){
        return ATHLETES.values().stream()
                .filter(athlete -> isAgeRange(athlete.getBirthDate(), minAge, maxAge))
                .toList();
    }

    @Override
    public List<AthleteData> getListBy(final int minAge, final int maxAge,
                                       final GenderEnum genderEnum,
                                       final CategoryEnum categoryEnum){
        return ATHLETES.values().stream()
                .filter(athlete -> GenderEnum.ALL.equals(genderEnum) || genderEnum.equals(athlete.getGender()))
                .filter(athlete -> isAgeRange(athlete.getBirthDate(), minAge, maxAge))
                .filter(athleteData -> CategoryEnum.ALL.equals(categoryEnum) || categoryEnum.equals(athleteData.getCategory()))
                .toList();
    }

    @Override
    public List<AthleteData> getListAll() {
        return ATHLETES.values().stream().toList();
    }

    private boolean isAgeRange(final LocalDate birthDate, final int min, final int max){
        final int currentYear = LocalDate.now().getYear();
        final int athleteAge = currentYear - birthDate.getYear();
        return athleteAge >= min && athleteAge <= max;
    }


}
