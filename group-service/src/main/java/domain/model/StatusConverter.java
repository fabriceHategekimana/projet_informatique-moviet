package domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)  // if remove it, need a @Converter on the attribute
public class StatusConverter implements AttributeConverter<Status, String> {
    /*
    We use the converter ! code copied from the first link
    https://www.baeldung.com/jpa-persisting-enums-in-jpa
    https://www.baeldung.com/jpa-mapping-single-entity-to-multiple-tables
     */
    @Override
    public String convertToDatabaseColumn(Status category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public Status convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Status.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}