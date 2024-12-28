package br.org.piba.sporting_event_race.utils;

import java.util.Collection;
import java.util.Objects;

public class CollectionUtils {

    private CollectionUtils(){}

    public static <T> boolean isNullOrEmpty(Collection<T> collection){
        return Objects.isNull(collection) || collection.isEmpty();
    }
}
