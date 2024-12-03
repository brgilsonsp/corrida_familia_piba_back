package br.org.piba.sporting_event_race.model.enumaration;

import br.org.piba.sporting_event_race.exception.CategoryNotFoundException;

public enum CategoryEnum {
    WALK("Caminhada"),
    RACE("Corrida"),
    ALL("Todas");

    private final String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static CategoryEnum getEnum(final String value){
        for(CategoryEnum categoryEnum : CategoryEnum.values()){
            if(categoryEnum.getValue().equalsIgnoreCase(value)){
                return categoryEnum;
            }
        }
        throw new CategoryNotFoundException("Categoria não localizada: " + value);
    }
}
