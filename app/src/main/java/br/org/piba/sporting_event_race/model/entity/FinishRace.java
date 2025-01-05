package br.org.piba.sporting_event_race.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"bibNumber", "timeFinish", "monitorName"})
@Entity
@Table(name = "finish_race")
public class FinishRace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_uuid", unique = true, nullable = false)
    private UUID idUuid;

    @Column(name = "bib_number", nullable = false)
    private Integer bibNumber;

    @Column(name = "time_finish", nullable = false)
    private LocalTime timeFinish;

    @Column(name = "monitor_name", nullable = false)
    private String monitorName;
}
