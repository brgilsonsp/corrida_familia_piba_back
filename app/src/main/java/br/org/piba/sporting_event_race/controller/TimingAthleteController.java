package br.org.piba.sporting_event_race.controller;

import br.org.piba.sporting_event_race.model.dto.RegistersTimingDTO;
import br.org.piba.sporting_event_race.service.TimingAthleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cronometros")
public class TimingAthleteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimingAthleteController.class);
    private final TimingAthleteService timingAthleteServiceImpl;

    public TimingAthleteController(TimingAthleteService timingAthleteServiceImpl) {
        this.timingAthleteServiceImpl = timingAthleteServiceImpl;
    }

    @PostMapping
    public ResponseEntity<RegistersTimingDTO> addTimingAthlete(@RequestBody RegistersTimingDTO timingsDTO){
        LOGGER.info("Cadastrando cronometro para {}", timingsDTO);
        return ResponseEntity.ok(timingAthleteServiceImpl.addTimingToAthlete(timingsDTO));
    }
}
