package br.org.piba.sporting_event_race.service;

import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.dto.ClassificationDTO;
import br.org.piba.sporting_event_race.model.enumaration.StatusTimerEnum;

import java.util.*;

public class ClassificationDefinitionServiceImpl implements ClassificationDefinitionService{

    public static final int POSITION_ZERO = 0;

    @Override
    public List<ClassificationDTO> makeClassification(final Map<StatusTimerEnum, List<AthleteData>> athletes) {
        athletes.get(StatusTimerEnum.WITH_TIMER).sort(Comparator.comparing(AthleteData::totalRaceTime));
        List<ClassificationDTO> classifications = new ArrayList<>();
        for (int i = 0; i < athletes.get(StatusTimerEnum.WITH_TIMER).size(); i++) {
            AthleteData data = athletes.get(StatusTimerEnum.WITH_TIMER).get(i);
            classifications.add(mapperToClassificationDTO(i + 1, data));
        }
        for(AthleteData data : athletes.get(StatusTimerEnum.NO_TIMER)){
            classifications.add(mapperToClassificationDTO(POSITION_ZERO, data));
        }

        return classifications;
    }

    private static ClassificationDTO mapperToClassificationDTO(final int position, final AthleteData data) {
        return new ClassificationDTO(position,
                data.getName(),
                data.getFormattedTotalRaceTime(),
                data.calculateAge(),
                data.getGender().getValue(),
                data.getNumber(),
                data.getCategory().getValue(),
                data.getOperator());
    }

    @Override
    public Map<StatusTimerEnum, List<AthleteData>> segregateListAthletes(List<AthleteData> originList) {
        List<AthleteData> athletesWithTimer = new ArrayList<>();
        List<AthleteData> athletesNoTimer = new ArrayList<>();
        for (AthleteData data : originList) {
            if (data.totalRaceTime().isZero()) {
                athletesNoTimer.add(data);
            } else {
                athletesWithTimer.add(data);
            }
        }
        Map<StatusTimerEnum, List<AthleteData>> mapSegregate = new HashMap<>();
        mapSegregate.put(StatusTimerEnum.WITH_TIMER, athletesWithTimer);
        mapSegregate.put(StatusTimerEnum.NO_TIMER, athletesNoTimer);
        return mapSegregate;
    }
}
