package br.org.piba.sporting_event_race.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final String messageClient;

    public BusinessException(final String messageClient){
        super(messageClient);
        this.messageClient = messageClient;
    }

    public BusinessException(final Throwable cause, final String messageClient){
        super(messageClient, cause);
        this.messageClient = messageClient;
    }

}
