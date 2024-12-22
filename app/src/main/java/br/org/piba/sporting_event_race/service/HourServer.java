package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.dto.ServerHourDTO;

@FunctionalInterface
public interface HourServer {

    ServerHourDTO getHour();
}
