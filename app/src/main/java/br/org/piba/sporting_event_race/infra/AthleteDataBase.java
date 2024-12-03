package br.org.piba.sporting_event_race.infra;

import br.org.piba.sporting_event_race.config.ServicesConfiguration;
import br.org.piba.sporting_event_race.model.domain.AthleteData;
import br.org.piba.sporting_event_race.model.enumaration.CategoryEnum;
import br.org.piba.sporting_event_race.model.enumaration.GenderEnum;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class AthleteDataBase {
    private static final Random RANDOMS = new Random();
    private static final Map<String, GenderEnum> NAME_GENDER_MAP;
    private static final GenderEnum MALE = GenderEnum.MALE;
    private static final GenderEnum FEMALE = GenderEnum.FEMALE;
    private static final Map<Integer, CategoryEnum> CATEGORIES;
    private static final Map<Integer, String> OPERATORS;
    private static final int MIN_AGE = 6;
    private static final int MAX_AGE = 70;
    private final static Map<UUID, AthleteData> ATHLETES;
    private static final int DECEMBER = 12;
    private static final int JANUARY = 1;
    private static final int DAY_ONE = 1;
    private static final int DAY_TWENTY_EIGHT = 28;
    public static final int MAX_CATEGORY = 2000;
    public static final int MIN_CATEGORY = 1;

    static {
        NAME_GENDER_MAP = new HashMap<>();
        // Adicionando nomes masculinos e seus respectivos gêneros
        NAME_GENDER_MAP.put("João Pedro Almeida Silva", MALE);
        NAME_GENDER_MAP.put("Lucas Henrique Oliveira Souza", MALE);
        NAME_GENDER_MAP.put("Gabriel Ferreira Costa", MALE);
        NAME_GENDER_MAP.put("Thiago Rodrigues Lima", MALE);
        NAME_GENDER_MAP.put("Matheus Santos Pereira", MALE);
        NAME_GENDER_MAP.put("Leonardo Alves Ribeiro", MALE);
        NAME_GENDER_MAP.put("Rafael Cunha Carvalho", MALE);
        NAME_GENDER_MAP.put("Bruno Mendes Araújo", MALE);
        NAME_GENDER_MAP.put("Diego Nogueira Barbosa", MALE);
        NAME_GENDER_MAP.put("Felipe Castro Gomes", MALE);
        NAME_GENDER_MAP.put("André Martins Figueira", MALE);
        NAME_GENDER_MAP.put("Gustavo Moraes Machado", MALE);
        NAME_GENDER_MAP.put("Ricardo Nascimento Correia", MALE);
        NAME_GENDER_MAP.put("Daniel Teixeira Monteiro", MALE);
        NAME_GENDER_MAP.put("Eduardo Araújo Fonseca", MALE);
        // Adicionando nomes femininos e seus respectivos gêneros
        NAME_GENDER_MAP.put("Maria Clara Fernandes Lima", FEMALE);
        NAME_GENDER_MAP.put("Ana Beatriz Oliveira Silva", FEMALE);
        NAME_GENDER_MAP.put("Letícia Martins Costa", FEMALE);
        NAME_GENDER_MAP.put("Júlia Santos Carvalho", FEMALE);
        NAME_GENDER_MAP.put("Camila Ferreira Sousa", FEMALE);
        NAME_GENDER_MAP.put("Larissa Almeida Pereira", FEMALE);
        NAME_GENDER_MAP.put("Isabela Souza Oliveira", FEMALE);
        NAME_GENDER_MAP.put("Mariana Cunha Ribeiro", FEMALE);
        NAME_GENDER_MAP.put("Bianca Rodrigues Nogueira", FEMALE);
        NAME_GENDER_MAP.put("Fernanda Mendes Castro", FEMALE);
        NAME_GENDER_MAP.put("Rafaela Barbosa Teixeira", FEMALE);
        NAME_GENDER_MAP.put("Aline Nogueira Araújo", FEMALE);
        NAME_GENDER_MAP.put("Vanessa Lima Fonseca", FEMALE);
        NAME_GENDER_MAP.put("Tatiane Gomes Nascimento", FEMALE);
        NAME_GENDER_MAP.put("Juliana Figueira Monteiro", FEMALE);

        CATEGORIES = new HashMap<>();
        CATEGORIES.put(MIN_CATEGORY, CategoryEnum.RACE);
        CATEGORIES.put(2, CategoryEnum.WALK);

        OPERATORS = new HashMap<>();
        OPERATORS.put(MIN_CATEGORY, "Carlos");
        OPERATORS.put(2, "Fernanda");
        OPERATORS.put(3, "Antonio");
        OPERATORS.put(4, "Bruno");

        ATHLETES = new HashMap<>();
        AthleteDataBase.NAME_GENDER_MAP
                .forEach((name, gender) -> {
                    AthleteData data = buildAthlete(name, gender);
                    ATHLETES.put(data.getId(), data);
                });

    }

    private static AthleteData buildAthlete(final String name, final GenderEnum genderEnum){
        final AthleteData data = new AthleteData(name, genderEnum, getRandomBirthDate(), getCategory());
        if(Boolean.parseBoolean(System.getProperty("mock", "false"))){
            configureAthlete(data);
        }
        return data;
    }

    private static void configureAthlete(final AthleteData data){
        data.setNumber(RANDOMS.nextInt(49877, 59877));
        data.setOperator(OPERATORS.get(RANDOMS.nextInt(1, OPERATORS.size())));
        data.setStartTime(ServicesConfiguration.START_TIME);
        final LocalDateTime arrivalTime = LocalDateTime.now()
                                .plusMinutes(RANDOMS.nextInt(30,70))
                                .plusSeconds(RANDOMS.nextInt(MIN_CATEGORY,30))
                                .plusNanos(RANDOMS.nextLong(1L,6000L));
        data.setArrivalTime(arrivalTime);
    }

    private static CategoryEnum getCategory() {
        final int indexRandom = RANDOMS.nextInt(MIN_CATEGORY, MAX_CATEGORY);
        final int index = indexRandom < (MAX_CATEGORY/2) ? 1 : 2;
        return CATEGORIES.get(index);
    }

    private static LocalDate getRandomBirthDate() {
        int currentYear = LocalDate.now().getYear();
        int randomYear = currentYear - RANDOMS.nextInt(MIN_AGE, MAX_AGE);
        Month randomMonth = Month.of(RANDOMS.nextInt(JANUARY, DECEMBER)); // Random month between 1 and 12
        int randomDay = RANDOMS.nextInt(DAY_ONE, DAY_TWENTY_EIGHT); // Random day between 1 and 28 to avoid invalid days
        return LocalDate.of(randomYear, randomMonth, randomDay);
    }

    public Map<UUID, AthleteData> getAthletes(){
        return ATHLETES;
    }
}
