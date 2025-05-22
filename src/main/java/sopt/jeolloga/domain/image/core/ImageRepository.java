package sopt.jeolloga.domain.image.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.image.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
