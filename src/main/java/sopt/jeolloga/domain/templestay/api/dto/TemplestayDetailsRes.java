package sopt.jeolloga.domain.templestay.api.dto;

public record TemplestayDetailsRes(
        Long id,
        String templestayName,
        String templeName,
        String address,
        String phone,
        String schedule,
        Integer price,
        String introduction,
        String url,
        Double lat,
        Double lon
) {
}
