package sopt.jeolloga.common.filter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sopt.jeolloga.common.filter.EtcOption;

import java.util.Arrays;

@Component
public class EtcOptionConverter implements Converter<String, EtcOption> {

    @Override
    public EtcOption convert(String source) {
        String trimmed = source.trim();
        return Arrays.stream(EtcOption.values())
                .filter(e -> e.name().equalsIgnoreCase(trimmed) || e.getLabel().equalsIgnoreCase(trimmed))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid EtcOption value: " + source));
    }
}
