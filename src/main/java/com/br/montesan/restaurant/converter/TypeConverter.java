package com.br.montesan.restaurant.converter;

import com.br.montesan.restaurant.enums.Type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, Long> {

    @Override
    public Long convertToDatabaseColumn(Type type) {
        if(type == null){
            return null;
        }
        return type.getCode();
    }

    @Override
    public Type convertToEntityAttribute(Long type) {
        if(type == null){
            return null;
        }
        return Stream.of(Type.values())
                     .filter(t -> t.getCode().equals(type))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
}
