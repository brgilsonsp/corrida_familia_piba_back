package br.org.piba.sporting_event_race.exception;

public class CategoryNotFoundException extends  RuntimeException{

    public CategoryNotFoundException(final String message){
        super(message);
    }
}
