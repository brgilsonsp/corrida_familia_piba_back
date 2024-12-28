package br.org.piba.sporting_event_race.exception;

public class RecordNotFoundException extends BusinessException{

    public RecordNotFoundException(String messageClient) {
        super(messageClient);
    }

    public RecordNotFoundException(Throwable cause, String messageClient) {
        super(cause, messageClient);
    }
}
