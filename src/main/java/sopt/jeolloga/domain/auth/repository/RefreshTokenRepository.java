package sopt.jeolloga.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import sopt.jeolloga.domain.auth.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
