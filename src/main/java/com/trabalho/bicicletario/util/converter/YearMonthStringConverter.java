package com.trabalho.bicicletario.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

@Converter(autoApply = true)
public class YearMonthStringConverter implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return (attribute == null) ? null : attribute.toString(); // Converte para "yyyy-MM"
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return (dbData == null || dbData.isEmpty()) ? null : YearMonth.parse(dbData);
    }
}