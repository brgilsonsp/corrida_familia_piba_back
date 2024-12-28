package br.org.piba.sporting_event_race.exception;

public class NoContentException extends BusinessException {

    public NoContentException( String messageClient) {
        super(messageClient);
    }

    public NoContentException(Throwable cause, String messageClient) {
        super(cause, messageClient);
    }
}
