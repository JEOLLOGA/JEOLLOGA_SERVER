package sopt.jeolloga.domain.templestay.mapper;

import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;

import java.util.Optional;

public class TemplestayDetailsMapper {

    public static Optional<TemplestayDetailsRes> map(
            Templestay templestay,
            Integer price,
            boolean wish
    ) {
        if (templestay.getLat() == null || templestay.getLon() == null) {
            return Optional.empty();
        }

        return Optional.of(new TemplestayDetailsRes(
                templestay.getId(),
                templestay.getOrganizedName(),
                templestay.getTempleName(),
                templestay.getAddress(),
                templestay.getPhone(),
                templestay.getSchedule(),
                price,
                templestay.getIntroduction(),
                templestay.getUrl(),
                templestay.getLat(),
                templestay.getLon(),
                wish
        ));
    }
}