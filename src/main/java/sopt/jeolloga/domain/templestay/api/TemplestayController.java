package sopt.jeolloga.domain.templestay.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendListRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendRes;
import sopt.jeolloga.domain.templestay.core.TemplestayService;

@RestController
@RequestMapping("/templestay")
public class TemplestayController {
    private final TemplestayService templestayService;

    public TemplestayController(TemplestayService templestayService) {
        this.templestayService = templestayService;
    }

    @GetMapping("/recommendation")
    public ResponseEntity<ApiResponse<?>> getRecommendTemplestay() {
        TemplestayRecommendListRes templestayRecommendRes = templestayService.getRecommendTemplestays();
        return ResponseEntity.ok(ApiResponse.success(templestayRecommendRes));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse<?>> getDetailsTemplestay(@PathVariable Long id) {
        TemplestayDetailsRes templestayDetailsRes = templestayService.getDetailsTemplestay(id);
        return ResponseEntity.ok(ApiResponse.success(templestayDetailsRes));
    }
}
