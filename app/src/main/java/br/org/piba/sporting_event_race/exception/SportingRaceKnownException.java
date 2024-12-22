package br.org.piba.sporting_event_race.exception;

public class SportingRaceKnownException extends RuntimeException{

    private final String messageClient;
    public SportingRaceKnownException(final String message, final String messageClient){
        super(message);
        this.messageClient = messageClient;
    }

    public SportingRaceKnownException(final String message, final Throwable cause, final String messageClient){
        super(message, cause);
        this.messageClient = messageClient;
    }

    public String getMessageClient(){
        return messageClient;
    }
}
