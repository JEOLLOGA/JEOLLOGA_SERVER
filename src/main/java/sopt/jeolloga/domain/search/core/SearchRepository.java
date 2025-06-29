package sopt.jeolloga.domain.search.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.search.Search;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    void deleteByMemberAndContent(Member member, String content);

    List<Search> findByMemberOrderByIdDesc(Member member);

    List<Search> findTop10ByMember_IdOrderByIdDesc(Long memberId);
}
