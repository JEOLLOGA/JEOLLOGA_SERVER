package sopt.jeolloga.domain.search.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.search.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

}
