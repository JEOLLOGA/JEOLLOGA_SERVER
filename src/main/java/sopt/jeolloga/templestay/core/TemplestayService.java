package sopt.jeolloga.templestay.core;

import org.springframework.stereotype.Service;

@Service
public class TemplestayService {
    private final TemplestayRepository templestayRepository;

    public TemplestayService(TemplestayRepository templestayRepository) {
        this.templestayRepository = templestayRepository;
    }
}
