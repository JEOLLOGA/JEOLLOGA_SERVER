package sopt.jeolloga.domain.image.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.image.Image;

import java.util.Optional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findFirstByTemplestayIdOrderByIdAsc(Long templestayId);
    List<Image> findByTemplestayId(Long templestayId);
    boolean existsByTemplestayId(Long templestayId);
}
