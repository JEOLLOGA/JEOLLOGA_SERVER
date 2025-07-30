package sopt.jeolloga.domain.templestay.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;

@Service
@RequiredArgsConstructor
public class ReviewApiService {
    private final TemplestayRepository templestayRepository;

}
