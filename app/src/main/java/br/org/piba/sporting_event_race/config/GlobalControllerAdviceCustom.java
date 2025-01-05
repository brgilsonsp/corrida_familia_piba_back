package br.org.piba.sporting_event_race.config;

import br.org.piba.sporting_event_race.exception.*;
import br.org.piba.sporting_event_race.model.dto.ResponseErrorDTO;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        logger.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseErrorDTO> businessError(BusinessException throwable){
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessageClient());
        logger.error(throwable.getMessageClient(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ResponseErrorDTO> noContent(NoContentException throwable){
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessageClient());
        logger.info(throwable.getMessageClient());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    @ExceptionHandler(RecordDuplicateException.class)
    public ResponseEntity<ResponseErrorDTO> recordDuplicate(RecordDuplicateException throwable){
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessageClient());
        logger.info(throwable.getMessageClient());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> notFound(RecordNotFoundException throwable){
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessageClient());
        logger.info(throwable.getMessageClient());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IncorrectRequestException.class)
    public ResponseEntity<ResponseErrorDTO> badRequest(IncorrectRequestException throwable){
        final ResponseErrorDTO error = getResponseErrorDTO(throwable.getMessageClient());
        logger.info(throwable.getMessageClient());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private static ResponseErrorDTO getResponseErrorDTO(final String message) {
        return new ResponseErrorDTO(message, UUID.randomUUID().toString());
    }


}
