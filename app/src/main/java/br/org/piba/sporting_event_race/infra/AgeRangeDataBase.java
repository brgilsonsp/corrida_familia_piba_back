package br.org.piba.sporting_event_race.infra;

import br.org.piba.sporting_event_race.model.domain.AgeRange;
import br.org.piba.sporting_event_race.model.enumaration.RangeAgeDefinitionEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgeRangeDataBase {

    public static final Map<RangeAgeDefinitionEnum, AgeRange> AGE_RANGES;
    public static final List<RangeAgeDefinitionEnum> RANGES = List.of(RangeAgeDefinitionEnum.values());
    static {
        AGE_RANGES = new HashMap<>();
        for(RangeAgeDefinitionEnum rangeEnum: RANGES){
            switch (rangeEnum){
                case SIXTH_ONE_OR_MORE -> AGE_RANGES.put(RangeAgeDefinitionEnum.SIXTH_ONE_OR_MORE, new AgeRange(61,200));
                case ALL -> AGE_RANGES.put(RangeAgeDefinitionEnum.ALL, new AgeRange(1,200));
                default -> {
                    String[] split = rangeEnum.getValue().split("-");
                    final int initial = Integer.parseInt(split[0].trim());
                    final int end = Integer.parseInt(split[1].trim());
                    AGE_RANGES.put(rangeEnum, new AgeRange(initial,end));
                }
            }
        }
    }

}
