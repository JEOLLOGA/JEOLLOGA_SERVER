package sopt.jeolloga.domain.image.api;

import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.domain.image.core.ImageService;

@RestController
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
}
