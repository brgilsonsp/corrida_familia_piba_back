package br.org.piba.sporting_event_race.repository;

import br.org.piba.sporting_event_race.model.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AthleteRepository extends JpaRepository<Athlete, Integer> {

    Athlete findFirstByDocument(String document);

    List<Athlete> findByNameStartingWith(String prefix);

    boolean existsByDocument(String document);

    Optional<Athlete> findByIdUuid(UUID idUuid);

    List<Athlete> findByBibNumberIn(List<Integer> bibNumber);

    @Query(value = "SELECT a.bib_number FROM athlete a WHERE a.bib_number IS NOT NULL", nativeQuery = true)
    List<Integer> findAllBibNumber();
}
