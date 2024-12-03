package br.org.piba.sporting_event_race.exception;

public class GenderNotFoundException extends RuntimeException{

    public GenderNotFoundException(final String message){
        super(message);
    }
}
