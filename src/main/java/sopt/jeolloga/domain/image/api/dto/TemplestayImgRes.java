package sopt.jeolloga.domain.image.api.dto;


import java.util.List;

public record TemplestayImgRes(
        Long templestayId,
        List<ImageRes> imgUrls
) {
}
