package sopt.jeolloga.domain.search.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.search.Search;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    void deleteByMemberAndContent(Member member, String content);

    List<Search> findTop10ByMember_IdOrderByIdDesc(Long memberId);

    @Query("SELECT s.id FROM Search s WHERE s.member = :member ORDER BY s.id DESC LIMIT 10")
    List<Long> findTop10IdsByMemberOrderByIdDesc(@Param("member") Member member);

    @Transactional
    @Modifying
    @Query("DELETE FROM Search s WHERE s.member = :member AND s.id NOT IN :keepIds")
    void deleteByMemberAndIdNotIn(@Param("member") Member member, @Param("keepIds") List<Long> keepIds);

    Optional<Search> findByIdAndMember_Id(Long id, Long memberId);

    void deleteByMember_Id(Long memberId);
}