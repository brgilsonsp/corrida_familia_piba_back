package br.org.piba.sporting_event_race.utils;

import java.time.format.DateTimeFormatter;

public final class DataTimeFormatterUtils {
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter FORMATTER_HOUR = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private DataTimeFormatterUtils(){}

}
