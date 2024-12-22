package br.org.piba.sporting_event_race.config;

import br.org.piba.sporting_event_race.exception.NumberAthleteUsedException;
import br.org.piba.sporting_event_race.exception.SportingRaceKnownException;
import br.org.piba.sporting_event_race.model.dto.ResponseErrorDTO;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.NoSuchElementException;
import java.util.UUID;

@ControllerAdvice
public class GlobalControllerAdviceCustom {
    private final Logger logger;

    public GlobalControllerAdviceCustom(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseErrorDTO> fatalError(Throwable throwable){
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(SportingRaceKnownException.class)
    public ResponseEntity<ResponseErrorDTO> knowError(SportingRaceKnownException throwable){
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessageClient());
        logger.error(throwable.getMessageClient(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseErrorDTO> notFound(NoSuchElementException exception){
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ResponseErrorDTO error = getResponseErrorDTO(exception.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NumberAthleteUsedException.class)
    public ResponseEntity<ResponseErrorDTO> numberUsed(NumberAthleteUsedException exception){
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ResponseErrorDTO error = getResponseErrorDTO(exception.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    private static ResponseErrorDTO getResponseErrorDTO(final String message) {
        return new ResponseErrorDTO(message, UUID.randomUUID().toString());
    }


}
