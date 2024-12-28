package br.org.piba.sporting_event_race.exception;

public class RecordDuplicateException extends BusinessException{
    public RecordDuplicateException(String messageClient) {
        super(messageClient);
    }

    public RecordDuplicateException(Throwable cause, String messageClient) {
        super(cause, messageClient);
    }
}
