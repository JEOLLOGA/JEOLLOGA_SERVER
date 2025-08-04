package sopt.jeolloga.domain.templestay.api.dto.res;

public record TemplestayDetailsRes(
        Long templestayId,
        String templestayName,
        String templeName,
        String address,
        String phone,
        String schedule,
        Integer price,
        String introduction,
        String url,
        Double lat,
        Double lon,
        boolean wish
) {
}
