package sopt.jeolloga.domain.templestay.core.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.core.repository.querydsl.TemplestayCustomRepository;

@Registered
public interface TemplestayRepository extends JpaRepository<Templestay, Long>, TemplestayCustomRepository {

}
