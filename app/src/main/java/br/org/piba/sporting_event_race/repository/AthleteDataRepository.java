package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AthleteDataRepository {

    Optional<AthleteData> getAthleteByNumber(int number);

    Optional<AthleteData> getAthleteById(UUID id);

    Optional<AthleteData> update(AthleteData athlete);

    List<AthleteData> getListBy(String startName);

    List<AthleteData> getListBy(GenderEnum genderEnum);

    List<AthleteData> getListBy(int minAge, int maxAge);

    List<AthleteData> getListBy(int minAge, int maxAge, GenderEnum genderEnum, CategoryEnum categoryEnum);

    List<AthleteData> getListAll();
}
