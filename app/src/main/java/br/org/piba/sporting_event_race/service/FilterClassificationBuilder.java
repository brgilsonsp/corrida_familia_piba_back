package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.bo.FilterClassificationBO;

public interface FilterClassificationBuilder {

    FilterClassificationBO builder(String ageRange, String gender);
}
