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
@EqualsAndHashCode(of = {"timeStart", "monitorName"})
@Entity
@Table(name = "start_race_general")
public class StartRaceGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_uuid", unique = true, nullable = false)
    private UUID idUuid;

    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name = "monitor_name", nullable = false)
    private String monitorName;
}
