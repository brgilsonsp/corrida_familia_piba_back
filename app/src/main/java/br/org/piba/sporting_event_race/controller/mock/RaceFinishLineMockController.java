package br.org.piba.sporting_event_race.controller.mock;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.dto.ErrorResponseDTO;
import br.org.piba.sporting_event_race.model.dto.FinishRaceDTO;
import br.org.piba.sporting_event_race.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.*;

import static br.org.piba.sporting_event_race.controller.mock.AthleteMockController.LIST_ATHLETE;

@RestController
@RequestMapping("/mock/cronometragem/chegadas")
public class RaceFinishLineMockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceFinishLineMockController.class);
    private static final List<FinishRaceDTO> LIST_FINISH_RACE = new ArrayList<>();
    public static final String ATHLETE_NAME = "Nome Atleta";

    @PostMapping
    public ResponseEntity<?> addNewRaceFinish(@RequestBody final FinishRaceDTO finishRaceDTO){
        try{
            final boolean hasAthlete = LIST_ATHLETE.stream()
                    .map(AthleteDTO::bibNumber)
                    .filter(Objects::nonNull)
                    .anyMatch(a -> a.equals(finishRaceDTO.bibNumber()));

            if(!hasAthlete){
                final String messageError = MessageFormat
                        .format("Atleta não encontrado com número de peito {0}. Registro de chegada não criado",
                                finishRaceDTO.bibNumber());
                LOGGER.warn(messageError);
                return buildRegisterInvalid400(messageError);
            }

            final Optional<FinishRaceDTO> registered = LIST_FINISH_RACE
                    .stream().filter(a -> a.equals(finishRaceDTO)).findFirst();
            if(registered.isPresent()){
                LOGGER.info("Race finish registered, not saved: {}", registered.get());
                return ResponseEntity.ok(registered.get());
            }else {
                final FinishRaceDTO newRaceFinish = new FinishRaceDTO(UUID.randomUUID(),
                        finishRaceDTO.bibNumber(),
                        finishRaceDTO.hour(),
                        finishRaceDTO.monitorName(),
                        ATHLETE_NAME);
                LIST_FINISH_RACE.add(newRaceFinish);
                LOGGER.info("Success add new race finish: {}", newRaceFinish);
                return ResponseEntity.status(201).body(newRaceFinish);
            }
        }catch (Exception e){
            LOGGER.error("Error add new race finish", e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<?> getRaceFinish(@RequestParam(name = "monitor", required = false) final String monitor,
                                          @RequestParam(name = "numero_peito", required = false) final Integer chesterNumber){
        try{
            final List<FinishRaceDTO> filtered = LIST_FINISH_RACE.stream()
                    .filter(s -> {
                        if (Objects.nonNull(monitor) && !monitor.isBlank()) {
                            return s.monitorName().equals(monitor);
                        }
                        return true;
                    })
                    .filter(s -> {
                        if (Objects.nonNull(chesterNumber) && chesterNumber > 0) {
                            return s.bibNumber().equals(chesterNumber);
                        }
                        return true;
                    }).toList();

            if(filtered.isEmpty()){
                LOGGER.warn("Race finish not found by monitor: {} and number: {}",
                        monitor, chesterNumber);
                return ResponseUtils.buildRegisterNotFound204();
            }else{
                LOGGER.info("Race finish found by monitor: {} and number: {}. List: {}",
                        monitor, chesterNumber, filtered);
                return ResponseEntity.ok(filtered);
            }
        }catch (Exception e){
            LOGGER.error("Error get list race finish", e);
            throw e;
        }
    }

    @PutMapping("/{id_cronometragem}")
    public ResponseEntity<?> updateRaceFinish(@PathVariable("id_cronometragem") final UUID id,
                                           @RequestBody final FinishRaceDTO finishRaceDTO){
        try{
            final boolean hasAthlete = LIST_ATHLETE.stream()
                    .map(AthleteDTO::bibNumber)
                    .filter(Objects::nonNull)
                    .anyMatch(a -> a.equals(finishRaceDTO.bibNumber()));

            if(!hasAthlete){
                final String messageError = MessageFormat
                        .format("Atleta não encontrado com número de peito {0}. Registro de chegada não atualizado",
                                finishRaceDTO.bibNumber());
                LOGGER.warn(messageError);
                return buildRegisterInvalid400(messageError);
            }

            Optional<FinishRaceDTO> optional = LIST_FINISH_RACE.stream()
                    .filter(s -> s.id().equals(id)).findFirst();

            if(optional.isPresent()){
                FinishRaceDTO newRegister = new FinishRaceDTO(id,
                        finishRaceDTO.bibNumber(),
                        finishRaceDTO.hour(),
                        finishRaceDTO.monitorName(),
                        ATHLETE_NAME);
                if(LIST_FINISH_RACE.remove(optional.get())){
                    LOGGER.info("Success update race finish {}", newRegister);
                    return ResponseEntity.ok(newRegister);
                }else{
                    LOGGER.warn("Cant update race finish {}", newRegister);
                    return ResponseUtils.buildError500();
                }
            }else{
                LOGGER.warn("Cant update race finish, because id not found {}", id);
                return ResponseUtils.buildRegisterNotFound404();
            }
        }catch (Exception e){
            LOGGER.error("Error update race finish", e);
            throw e;
        }

    }

    @DeleteMapping("/{id_cronometragem}")
    public ResponseEntity<?> deleteRaceFinish(@PathVariable("id_cronometragem") final UUID id){
        try{
            Optional<FinishRaceDTO> optional = LIST_FINISH_RACE.stream()
                    .filter(s -> s.id().equals(id)).findFirst();

            if(optional.isPresent()){
                if(LIST_FINISH_RACE.remove(optional.get())){
                    LOGGER.info("Success delete race finish {}", id);
                    return ResponseEntity.ok().build();
                }else{
                    LOGGER.warn("Cant delete race finish {}", id);
                    return ResponseUtils.buildError500();
                }
            }else{
                LOGGER.warn("Cant delete race finish, because id not found {}", id);
                return ResponseUtils.buildRegisterNotFound404();
            }
        }catch (Exception e){
            LOGGER.error("Error delete race finish", e);
            throw e;
        }
    }

    public static ResponseEntity<ErrorResponseDTO> buildRegisterInvalid400(final String message){
        return ResponseEntity.status(400).body(ResponseUtils.buildError(message));
    }
}
