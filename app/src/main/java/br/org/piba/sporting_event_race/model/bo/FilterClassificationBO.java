package br.org.piba.sporting_event_race.model.bo;

import br.org.piba.sporting_event_race.exception.BusinessException;
import br.org.piba.sporting_event_race.model.enums.GenderEnum;
import br.org.piba.sporting_event_race.utils.Assertion;
import io.micrometer.common.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class FilterClassificationBO {

    private static final String STANDARD_RANGE = "1-200";
    private static final int MAX_AGE = 200;
    public static final String FIELD_AGE_RANGE = "faixa etaria";

    private static int partInt(final String ageRange){
        try{
            Assertion.isNotBlank(ageRange, FIELD_AGE_RANGE);
            return Integer.parseInt(ageRange.replaceAll("\\D", ""));
        }catch (NumberFormatException e){
            throw new BusinessException(e, "Erro ao extrair a faixa etária " + ageRange);
        }
    }

    private final int initialAge;
    private final int endAge;
    private final GenderEnum gender;

    public FilterClassificationBO(final String ageRange, final String gender){
        final String[] ageRangeSplit = StringUtils.isNotBlank(ageRange) ?
                ageRange.split("-"): STANDARD_RANGE.split("-");
        Assertion.collectionIsNotEmpty(ageRangeSplit, FIELD_AGE_RANGE);

        if(ageRangeSplit.length == 1){
            this.initialAge = partInt(ageRangeSplit[0]);
            this.endAge = MAX_AGE;
        }else{
            this.initialAge = partInt(ageRangeSplit[0]);
            this.endAge = partInt(ageRangeSplit[1]);
        }
        this.gender = GenderEnum.parseValue(gender);
    }

}
