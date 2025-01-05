package br.org.piba.sporting_event_race.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "document")
@ToString
@Entity
@Table(name = "athlete")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_uuid", unique = true, nullable = false)
    private UUID idUuid;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    @Column(name = "athlete_name", nullable = false)
    private String athleteName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "modality", nullable = false)
    private String modality;

    @Column(name = "bib_number")
    private Integer bibNumber;

    @Column(name = "monitor_name")
    private String monitorName;
}
