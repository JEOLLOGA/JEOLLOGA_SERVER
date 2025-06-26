package sopt.jeolloga.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sopt.jeolloga.common.filter.converter.ActivityConverter;
import sopt.jeolloga.common.filter.converter.EtcOptionConverter;
import sopt.jeolloga.common.filter.converter.RegionConverter;
import sopt.jeolloga.common.filter.converter.TypeConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RegionConverter regionConverter;
    private final TypeConverter typeConverter;
    private final ActivityConverter activityConverter;
    private final EtcOptionConverter etcOptionConverter;

    public WebConfig(
            RegionConverter regionConverter,
            TypeConverter typeConverter,
            ActivityConverter activityConverter,
            EtcOptionConverter etcOptionConverter
    ) {
        this.regionConverter = regionConverter;
        this.typeConverter = typeConverter;
        this.activityConverter = activityConverter;
        this.etcOptionConverter = etcOptionConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(regionConverter);
        registry.addConverter(typeConverter);
        registry.addConverter(activityConverter);
        registry.addConverter(etcOptionConverter);
    }
}

