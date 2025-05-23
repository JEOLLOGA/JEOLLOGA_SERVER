package sopt.jeolloga.domain.image.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.image.api.dto.TemplestayImgRes;
import sopt.jeolloga.domain.image.core.ImageService;

@RestController
@RequestMapping("/templestay")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ApiResponse<?>> getTemplestayImgs(@PathVariable Long id) {
        TemplestayImgRes templestayImgRes = imageService.getTemplestayImgs(id);
        return ResponseEntity.ok(ApiResponse.success(templestayImgRes));
    }
}
