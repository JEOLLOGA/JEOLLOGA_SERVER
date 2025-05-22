package sopt.jeolloga.domain.filter.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.filter.Filter;

import java.util.Optional;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
    Optional<Filter> findFirstByTemplestayId(Long templestayId);
}
