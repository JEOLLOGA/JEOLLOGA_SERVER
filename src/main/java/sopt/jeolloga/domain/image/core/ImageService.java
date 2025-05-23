package sopt.jeolloga.domain.image.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.domain.image.Image;
import sopt.jeolloga.domain.image.api.dto.ImageRes;
import sopt.jeolloga.domain.image.api.dto.TemplestayImgRes;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional(readOnly = true)
    public TemplestayImgRes getTemplestayImgs(Long templestayId) {
        boolean exists = imageRepository.existsByTemplestayId(templestayId);
        if (!exists) {
            throw new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY);
        }

        List<Image> imageEntities = imageRepository.findByTemplestayId(templestayId);

        List<ImageRes> images = imageEntities.stream()
                .map(image -> new ImageRes(image.getImgUrl()))
                .toList();

        return new TemplestayImgRes(templestayId, images);
    }
}
