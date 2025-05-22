package sopt.jeolloga.domain.filter.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.filter.Filter;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {

}
