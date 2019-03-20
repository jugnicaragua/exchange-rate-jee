package ni.jug.exchangerate.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author aalaniz
 */
@Converter(autoApply = true)
public class BankConverter implements AttributeConverter<Bank, String> {

    @Override
    public String convertToDatabaseColumn(Bank bank) {
        return bank != null ? bank.name() : null;
    }

    @Override
    public Bank convertToEntityAttribute(String dbData) {
        return Bank.valueOf(dbData);
    }

}
