package sopt.jeolloga.common.filter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sopt.jeolloga.common.filter.Type;

import java.util.Arrays;

@Component
public class TypeConverter implements Converter<String, Type> {

    @Override
    public Type convert(String source) {
        String trimmed = source.trim();
        return Arrays.stream(Type.values())
                .filter(t -> t.name().equalsIgnoreCase(trimmed) || t.getLabel().equalsIgnoreCase(trimmed))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Type value: " + source));
    }
}
