package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.exception.SportingRaceKnownException;
import br.org.piba.sporting_event_race.model.dto.ServerHourDTO;
import br.org.piba.sporting_event_race.service.HourServer;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class HourServerService implements HourServer {

    public static final String MESSAGE_INTERN = "Error while get server hour";
    public static final String MESSAGE_CLIENT = "Ocorreu erro ao obter a hora do servidor";
    private final DateTimeFormatter hourFormat;

    public HourServerService() {
        hourFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Override
    public ServerHourDTO getHour() {
        try{
            final String hourServer = LocalTime.now().format(hourFormat);
            return new ServerHourDTO(hourServer);
        }catch (DateTimeException e){
            throw new SportingRaceKnownException(MESSAGE_INTERN, e, MESSAGE_CLIENT);
        }
    }
}
