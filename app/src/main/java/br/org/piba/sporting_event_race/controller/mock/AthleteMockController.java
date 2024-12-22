package br.org.piba.sporting_event_race.controller.mock;

import br.org.piba.sporting_event_race.model.dto.AthleteDTO;
import br.org.piba.sporting_event_race.model.dto.ErrorResponseDTO;
import br.org.piba.sporting_event_race.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mock/atletas")
public class AthleteMockController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AthleteMockController.class);
    public static final List<AthleteDTO> LIST_ATHLETE;
    public static final String ATHLETE_NOT_FOUND = "Atleta não encontrato";
    public static final String ERROR_UPDATE = "Erro ao atualizar registro";

    static{
        AthleteDTO anaCristina = new AthleteDTO(UUID.randomUUID(),
                "Ana Cristina",
                "145.947.750-20",
                12345,
                "Feminino",
                "17/12/2000",
                "Caminhada",
                "Lucas");
        AthleteDTO paulo = new AthleteDTO(UUID.randomUUID(),
                "Paulo",
                "770.455.510-37",
                4321,
                "Masculino",
                "07/09/1997",
                "Corrida",
                "Lucas");
        AthleteDTO ricardo = new AthleteDTO(UUID.randomUUID(),
                "Ricardo",
                "757.677.430-45",
                657687,
                "Masculino",
                "23/01/1976",
                "Corrida",
                "Samantha");

        LIST_ATHLETE = new ArrayList<>();
        LIST_ATHLETE.add(anaCristina);
        LIST_ATHLETE.add(paulo);
        LIST_ATHLETE.add(ricardo);
    }

    @GetMapping
    public ResponseEntity<List<AthleteDTO>> getAthlete(@RequestParam(name = "documento", required = false) final String document,
                                                              @RequestParam(name = "nome_parcial", required = false) final String partialName){

        try{
            List<AthleteDTO> athleteFiltered = LIST_ATHLETE
                    .stream()
                    .filter(a -> {
                        if (Objects.nonNull(document) && !document.isBlank()) {
                            return a.document().equals(document);
                        }
                        return true;
                    })
                    .filter(a -> {
                        if (Objects.nonNull(partialName) && !partialName.isBlank()) {
                            return a.names().contains(partialName);
                        }
                        return true;
                    })
                    .toList();
            LOGGER.info("Success get list of athlete by document: {} and partial name: {}. List {}",
                    document, partialName, athleteFiltered);
            return ResponseEntity.ok(athleteFiltered);
        }catch (Exception e){
            LOGGER.error("Error get list of athlete", e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewAthlete(@RequestBody final AthleteDTO athlete){
        try{
            if(invalidByMonitorName(athlete)){
                LOGGER.warn("Error add new athlete. Sent number but not sent monitor");
                return buildError400();
            }

            Optional<AthleteDTO> athleteSaved = LIST_ATHLETE.stream()
                    .filter(a -> a.document().equalsIgnoreCase(athlete.document())).findFirst();
            if(athleteSaved.isPresent()){
                LOGGER.warn("Athlete exist, not created. Athlete saved {}, athlete sent {}",
                        athleteSaved.get(), athlete);
                return buildError409("Localizado um registro com esse documento: " + athlete.document() + ".");
            }else {
                AthleteDTO newAthlete = new AthleteDTO(UUID.randomUUID(),
                        athlete.names(),
                        athlete.document(),
                        athlete.chesterNumber(),
                        athlete.gender(),
                        athlete.birthDate(),
                        athlete.mode(),
                        athlete.monitorName());
                LIST_ATHLETE.add(newAthlete);
                LOGGER.info("Success add new athlete: {}", newAthlete);
                return ResponseEntity.status(201).body(newAthlete);
            }
        }catch (Exception e){
            LOGGER.error("Error add new athlete", e);
            throw e;
        }

    }

    @DeleteMapping("/{id_atleta}")
    public ResponseEntity<?> removeAthlete(@PathVariable("id_atleta") final UUID idAthlete){
        try{
            Optional<AthleteDTO> athlete = LIST_ATHLETE.stream()
                    .filter(a -> a.id().equals(idAthlete))
                    .findFirst();
            if(athlete.isPresent()){
                if(LIST_ATHLETE.remove(athlete.get())){
                    LOGGER.info("Deleted athlete with Id {}", idAthlete);
                    return ResponseEntity.ok().build();
                }else{
                    LOGGER.warn("Not deleted athlete with Id {}", idAthlete);
                    return buildError500("Erro ao excluir o atleta");
                }
            }else{
                LOGGER.warn("Id athlete not found: {}", idAthlete);
                return buildError404();
            }
        }catch (Exception e){
            LOGGER.error("Error delete athlete", e);
            throw e;
        }
    }

    @PutMapping("/{id_atleta}")
    public ResponseEntity<?> updateAthlete(@PathVariable("id_atleta") final UUID idAthlete,
                                           @RequestBody final AthleteDTO athlete){
        try{
            if(invalidByMonitorName(athlete)){
                LOGGER.warn("Error update athlete. Sent number but not sent monitor");
                return buildError400();
            }

            Optional<AthleteDTO> athleteFound = LIST_ATHLETE.stream()
                    .filter(a -> a.id().equals(idAthlete))
                    .findFirst();
            if(athleteFound.isPresent()){
                AthleteDTO newAthlete = new AthleteDTO(idAthlete,
                        athlete.names(),
                        athlete.document(),
                        athlete.chesterNumber(),
                        athlete.gender(),
                        athlete.birthDate(),
                        athlete.mode(),
                        athlete.monitorName());
                if(LIST_ATHLETE.remove(athleteFound.get())){
                    LIST_ATHLETE.add(newAthlete);
                    LOGGER.info("Success update athlete: {}", newAthlete);
                    return ResponseEntity.ok(newAthlete);
                }else{
                    LOGGER.warn("Cant update athlete: {}", newAthlete);
                    return buildError500(ERROR_UPDATE);
                }
            }else{
                LOGGER.warn("Cant update athlete, because id not found {}", idAthlete);
                return buildError404();
            }
        }catch (Exception e){
            LOGGER.error("Error update athlete", e);
            throw e;
        }

    }

    private static boolean invalidByMonitorName(final AthleteDTO athlete){
        return (Objects.nonNull(athlete.chesterNumber()) && athlete.chesterNumber() > 0) &&
                (Objects.isNull(athlete.monitorName()) || athlete.monitorName().isBlank());
    }

    private static ResponseEntity<ErrorResponseDTO> buildError400(){
        return ResponseEntity.status(400)
                .body(ResponseUtils.buildError("Nome do monitor é obrigatório quando numero do peito é informado"));
    }

    private static ResponseEntity<ErrorResponseDTO> buildError404(){
        return ResponseEntity.status(404).body(ResponseUtils.buildError(AthleteMockController.ATHLETE_NOT_FOUND));
    }

    private static ResponseEntity<ErrorResponseDTO> buildError409(final String message){
        return ResponseEntity.status(409).body(ResponseUtils.buildError(message));
    }

    private static ResponseEntity<ErrorResponseDTO> buildError500(final String message){
        return ResponseEntity.status(500).body(ResponseUtils.buildError(message));
    }

}
