package sopt.jeolloga.domain.templestay.mapper;

import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;

import java.util.Optional;

public class TemplestayDetailsMapper {

    public static Optional<TemplestayDetailsRes> validateLatLon(TemplestayDetailsRes details) {
        if (details.lat() == null || details.lon() == null) {
            return Optional.empty();
        }
        return Optional.of(details);
    }
}
