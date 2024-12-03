package br.org.piba.sporting_event_race.service;

import java.time.LocalDateTime;

public class StartRaceServiceMock implements StartRaceService{

    private final LocalDateTime localTime;

    public StartRaceServiceMock(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public LocalDateTime getStartRace() {
        return localTime;
    }
}
