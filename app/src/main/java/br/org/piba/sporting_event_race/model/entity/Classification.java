package br.org.piba.sporting_event_race.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"bibNumber", "athleteName", "modality"})
@Entity
@Table(name = "classification")
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bib_number", nullable = false)
    private Integer bibNumber;

    @Column(name = "athlete_name", nullable = false)
    private String athleteName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "modality", nullable = false)
    private String modality;

    @Column(name = "total_time", nullable = false)
    private LocalTime totalTime;

    @Column(name = "monitor_name_finish_race", nullable = false)
    private String monitorNameFinishRace;
}
