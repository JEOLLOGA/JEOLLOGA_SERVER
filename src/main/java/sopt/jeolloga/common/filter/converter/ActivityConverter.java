package sopt.jeolloga.common.filter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sopt.jeolloga.common.filter.Activity;

import java.util.Arrays;

@Component
public class ActivityConverter implements Converter<String, Activity> {

    @Override
    public Activity convert(String source) {
        String trimmed = source.trim();
        return Arrays.stream(Activity.values())
                .filter(a -> a.name().equalsIgnoreCase(trimmed) || a.getLabel().equalsIgnoreCase(trimmed))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Activity value: " + source));
    }
}
