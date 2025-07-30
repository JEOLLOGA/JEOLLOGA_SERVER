package sopt.jeolloga.domain.templestay.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.core.repository.querydsl.TemplestayCustomRepository;

import java.util.List;

public interface TemplestayRepository
        extends JpaRepository<Templestay, Long>, TemplestayCustomRepository {
    @Query("SELECT DISTINCT t.templeName FROM Templestay t")
    List<String> findDistinctTempleNames();

    @Query("SELECT t.templeName FROM Templestay t WHERE t.id = :templestayId")
    String findTempleNameById(@Param("templestayId") Long templestayId);
}
