package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassificationRepository extends JpaRepository<Classification, Integer> {

    List<Classification> findByAgeBetween(int startAge, int endAge);

    List<Classification> findByGenderAndAgeBetween(String gender, int startAge, int endAge);
}
