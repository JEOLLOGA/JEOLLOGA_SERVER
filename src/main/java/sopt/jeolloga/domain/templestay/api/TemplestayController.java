package sopt.jeolloga.domain.templestay.api;

import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.domain.templestay.core.TemplestayService;

@RestController
public class TemplestayController {
    private final TemplestayService templestayService;

    public TemplestayController(TemplestayService templestayService) {
        this.templestayService = templestayService;
    }
}
