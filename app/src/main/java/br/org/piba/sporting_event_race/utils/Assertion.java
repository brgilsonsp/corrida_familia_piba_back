package br.org.piba.sporting_event_race.utils;

import br.org.piba.sporting_event_race.exception.BusinessException;

import java.util.Objects;

public final class Assertion {

    private Assertion(){}

    public static void isNotBlank(final String string, final String fieldName){
        if(Objects.isNull(string) || string.isBlank()){
            throw new BusinessException("O campo " + fieldName + " não pode nulo ou vazio");
        }
    }

    public static void collectionIsNotEmpty(final Object[] collection, final String fieldName){
        if(Objects.isNull(collection) || collection.length == 0){
            throw new BusinessException("O campo " + fieldName + " incorreto");
        }
    }
}
