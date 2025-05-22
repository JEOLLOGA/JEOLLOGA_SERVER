package sopt.jeolloga.templestay.core;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import sopt.jeolloga.templestay.Templestay;

@Registered
public interface TemplestayRepository extends JpaRepository<Templestay, Long> {

}
