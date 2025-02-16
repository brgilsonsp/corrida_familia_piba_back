package br.org.piba.sporting_event_race.controller.mock;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.dto.ErrorResponseDTO;
import br.org.piba.sporting_event_race.model.dto.StartRaceDTO;
import br.org.piba.sporting_event_race.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.*;

import static br.org.piba.sporting_event_race.controller.mock.AthleteMockController.LIST_ATHLETE;

@RestController
@RequestMapping("/mock/cronometragem/largadas")
public class StartTimeMockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartTimeMockController.class);
    private static final List<StartRaceDTO> LIST_START_TIME_LINE = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> getStartLine(@RequestParam(name = "monitor", required = false) final String monitor,
                                        @RequestParam(name = "numero_peito", required = false) final Integer chesterNumber){
        try{
            List<StartRaceDTO> filtered = LIST_START_TIME_LINE.stream()
                    .filter(s -> {
                        if (Objects.nonNull(monitor) && !monitor.isBlank()) {
                            return s.monitorName().equals(monitor);
                        }
                        return true;
                    })
                    .filter(s -> {
                        if (Objects.nonNull(chesterNumber) && chesterNumber > 0) {
                            return s.bibNumber() == chesterNumber;
                        }
                        return true;
                    }).toList();

            if(filtered.isEmpty()){
                LOGGER.warn("Cant get start line, because not found by monitor: {} and number: {}",
                        monitor, chesterNumber);
                return ResponseUtils.buildRegisterNotFound204();
            }else{
                LOGGER.warn("Get start line by monitor: {} and number: {}. List: {}",
                        monitor, chesterNumber, filtered);
                return ResponseEntity.ok(filtered);
            }
        }catch (Exception e){
            LOGGER.error("Error get start line", e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewStartLine(@RequestBody final StartRaceDTO startRaceDTO){
        try{
            final boolean hasAthlete = LIST_ATHLETE.stream()
                    .map(AthleteDTO::bibNumber)
                    .filter(Objects::nonNull)
                    .anyMatch(a -> a.equals(startRaceDTO.bibNumber()));

            if(!hasAthlete){
                final String messageError = MessageFormat
                        .format("Atleta não encontrado com número de peito {0}. Registro de largada não criado",
                                startRaceDTO.bibNumber());
                LOGGER.warn(messageError);
                return buildRegisterInvalid400(messageError);
            }

            final Optional<StartRaceDTO> registered = LIST_START_TIME_LINE.stream()
                    .filter(s -> s.equals(startRaceDTO)).findFirst();
            if(registered.isPresent()) {
                LOGGER.info("Register start line not savedd, because exists: {}", registered.get());
                return ResponseEntity.ok(registered.get());
            }else{

                StartRaceDTO newStartRaceDTO = new StartRaceDTO(UUID.randomUUID(),
                        startRaceDTO.bibNumber(),
                        startRaceDTO.arrivalTime(),
                        startRaceDTO.monitorName(),
                        startRaceDTO.athleteName());
                LIST_START_TIME_LINE.add(newStartRaceDTO);
                LOGGER.info("Success add new start line: {}", newStartRaceDTO);
                return ResponseEntity.status(201).body(newStartRaceDTO);
            }
        }catch (Exception e){
            LOGGER.error("Error add new start line", e);
            throw e;
        }
    }

    @PutMapping("/{id_registro}")
    public ResponseEntity<?> updateStartLine(@PathVariable("id_registro") final UUID idStartLine,
                                             @RequestBody final StartRaceDTO startRaceDTO){
        try{
            Optional<StartRaceDTO> startLineFound = LIST_START_TIME_LINE.stream()
                    .filter(s -> s.id().equals(idStartLine)).findFirst();

            final boolean hasAthlete = LIST_ATHLETE.stream()
                    .map(AthleteDTO::bibNumber)
                    .filter(Objects::nonNull)
                    .anyMatch(a -> a.equals(startRaceDTO.bibNumber()));

            if(!hasAthlete){
                final String messageError = MessageFormat
                        .format("Atleta não encontrado com número de peito {0}. egistro de largada não atualizado",
                                startRaceDTO.bibNumber());
                LOGGER.warn(messageError);
                return buildRegisterInvalid400(messageError);
            }
            if(startLineFound.isPresent()){
                StartRaceDTO newStartRaceDTO = new StartRaceDTO(idStartLine,
                        startRaceDTO.bibNumber(),
                        startRaceDTO.arrivalTime(),
                        startRaceDTO.monitorName(),
                        startRaceDTO.athleteName());
                if(LIST_START_TIME_LINE.remove(startLineFound.get())){
                    LIST_START_TIME_LINE.add(newStartRaceDTO);
                    LOGGER.info("Success update start line {}", newStartRaceDTO);
                    return ResponseEntity.ok(newStartRaceDTO);
                }else{
                    LOGGER.warn("Cant update start line {}", newStartRaceDTO);
                    return ResponseUtils.buildError500();
                }
            }else{
                LOGGER.warn("Cant update start line, because id not found {}", idStartLine);
                return ResponseUtils.buildRegisterNotFound404();
            }
        }catch (Exception e){
            LOGGER.error("Error update start line", e);
            throw e;
        }
    }

    @DeleteMapping("/{id_registro}")
    public ResponseEntity<?> removeStartLine(@PathVariable("id_registro") final UUID idStartLine){
        try{
            Optional<StartRaceDTO> startLineFound = LIST_START_TIME_LINE.stream()
                    .filter(s -> s.id().equals(idStartLine)).findFirst();

            if(startLineFound.isPresent()){
                if(LIST_START_TIME_LINE.remove(startLineFound.get())){
                    LOGGER.info("Success delete start line {}", idStartLine);
                    return ResponseEntity.ok().build();
                }else{
                    LOGGER.warn("Cant delete start line {}", idStartLine);
                    return ResponseUtils.buildError500();
                }
            }else{
                LOGGER.warn("Cant delete start line, because id not found {}", idStartLine);
                return ResponseUtils.buildRegisterNotFound404();
            }
        }catch (Exception e){
            LOGGER.error("Error delete start line", e);
            throw e;
        }
    }

    public static ResponseEntity<ErrorResponseDTO> buildRegisterInvalid400(final String message){
        return ResponseEntity.status(400).body(ResponseUtils.buildError(message));
    }

}
