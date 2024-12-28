package br.org.piba.sporting_event_race.exception;

public class IncorrectRequestException extends BusinessException{

    public IncorrectRequestException(String messageClient) {
        super(messageClient);
    }

    public IncorrectRequestException(Throwable cause, String messageClient) {
        super(cause, messageClient);
    }
}
