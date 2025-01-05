package br.org.piba.sporting_event_race.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"timeFinishGeneral", "statusGeneralRace"})
@Builder
@Getter
@Setter
@Entity
@Table(name ="status_finish_general_race")
public class StatusFinishGeneralRace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time_finish_general", unique = true, nullable = false)
    private LocalTime timeFinishGeneral;

    @Column(name = "status_general_race", nullable = false)
    private String statusGeneralRace;

    @Column(name = "monitor_name", nullable = false)
    private String monitorName;
}
