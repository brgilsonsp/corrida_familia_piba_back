package br.org.piba.sporting_event_race.utils;


import java.util.UUID;

public class RandomUUID {
    private RandomUUID(){}

    public static String newUUIDString(){
        return UUID.randomUUID().toString();
    }

    public static UUID newUUID(){
        return UUID.randomUUID();
    }
}
