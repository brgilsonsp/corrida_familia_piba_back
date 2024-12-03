package br.org.piba.sporting_event_race.config;

import br.org.piba.sporting_event_race.exception.NumberAthleteUsedException;
import br.org.piba.sporting_event_race.model.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerAdviceCustom {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseErrorDTO> fatalError(Throwable throwable){
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ResponseErrorDTO error = getResponseErrorDTO(throwable, status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseErrorDTO> notFound(NoSuchElementException exception){
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ResponseErrorDTO error = getResponseErrorDTO(exception, status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NumberAthleteUsedException.class)
    public ResponseEntity<ResponseErrorDTO> numberUsed(NumberAthleteUsedException exception){
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ResponseErrorDTO error = getResponseErrorDTO(exception, status);
        return ResponseEntity.status(status).body(error);
    }

    private static ResponseErrorDTO getResponseErrorDTO(final Throwable throwable,
                                                        final HttpStatus httpStatus) {
        return new ResponseErrorDTO(throwable.getMessage(), httpStatus.name());
    }


}
