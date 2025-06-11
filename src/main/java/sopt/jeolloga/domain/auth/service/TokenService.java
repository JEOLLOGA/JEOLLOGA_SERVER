package sopt.jeolloga.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.auth.RefreshToken;
import sopt.jeolloga.domain.auth.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void save(Long userId, String refreshToken) {
        refreshTokenRepository.save(new RefreshToken("RT:" + userId, refreshToken));
    }

    public boolean validate(Long userId, String refreshToken) {
        return refreshTokenRepository.findById("RT:" + userId)
                .map(stored -> stored.getValue().equals(refreshToken))
                .orElse(false);
    }

    public void delete(Long userId) {
        refreshTokenRepository.deleteById("RT:" + userId);
    }
}