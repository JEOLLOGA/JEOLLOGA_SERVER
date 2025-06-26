package sopt.jeolloga.domain.image.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.image.Image;

import java.util.Optional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findFirstByTemplestay_IdOrderByIdAsc(Long templestayId);
    List<Image> findByTemplestay_Id(Long templestayId);
    boolean existsByTemplestay_Id(Long templestayId);
    @Query("""
    SELECT i
    FROM Image i
    WHERE i.id IN (
        SELECT MIN(i2.id)
        FROM Image i2
        WHERE i2.templestay.id IN :templestayIds
        GROUP BY i2.templestay.id
    )
    """)
    List<Image> findFirstImagesByTemplestayIds(@Param("templestayIds") List<Long> templestayIds);
}
