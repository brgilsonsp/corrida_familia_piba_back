package br.org.piba.sporting_event_race.service.impl;

import br.org.piba.sporting_event_race.model.bo.FilterClassificationBO;
import br.org.piba.sporting_event_race.service.FilterClassificationBuilder;
import org.springframework.stereotype.Service;

@Service
public class FilterClassificationBuilderImpl implements FilterClassificationBuilder {

    @Override
    public FilterClassificationBO builder(String ageRange, String gender) {
        return new FilterClassificationBO(ageRange, gender);
    }
}
