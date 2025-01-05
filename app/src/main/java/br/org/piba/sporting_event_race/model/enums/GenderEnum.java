package br.org.piba.sporting_event_race.model.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    FEMALE("Feminino"),
    MALE("Masculino"),
    ALL("Todos");

    public static GenderEnum parseValue(final String value){
        for(GenderEnum genderEnum : GenderEnum.values()){
            if(genderEnum.getValue().equalsIgnoreCase(value)){
                return genderEnum;
            }
        }
        return GenderEnum.ALL;
    }

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

}
