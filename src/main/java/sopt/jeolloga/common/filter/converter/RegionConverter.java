package sopt.jeolloga.common.filter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sopt.jeolloga.common.filter.Region;

import java.util.Arrays;

@Component
public class RegionConverter implements Converter<String, Region> {

    @Override
    public Region convert(String source) {
        String trimmed = source.trim();
        return Arrays.stream(Region.values())
                .filter(r -> r.name().equalsIgnoreCase(trimmed) || r.getLabel().equalsIgnoreCase(trimmed))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Region value: " + source));
    }
}
