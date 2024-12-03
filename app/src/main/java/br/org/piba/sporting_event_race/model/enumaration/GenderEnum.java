package br.org.piba.sporting_event_race.model.enumaration;

import br.org.piba.sporting_event_race.exception.GenderNotFoundException;

public enum GenderEnum {

    MALE("Masculino"),
    FEMALE("Feminino"),
    ALL("Todos");

    private final String value;

    GenderEnum(String genderName) {
        this.value = genderName;
    }

    public String getValue() {
        return value;
    }

    public static GenderEnum getEnum(final String gender){
        for(GenderEnum genderEnum : GenderEnum.values()){
            if(genderEnum.getValue().equalsIgnoreCase(gender)){
                return genderEnum;
            }
        }
        throw new GenderNotFoundException("Sexo informado não existe: " + gender);
    }
}
