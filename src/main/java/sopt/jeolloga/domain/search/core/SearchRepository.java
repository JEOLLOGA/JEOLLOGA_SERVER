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

    @Transactional
    @Modifying
    @Query("""
        DELETE FROM Search s
        WHERE s.member = :member
        AND s.id NOT IN (
            SELECT s2.id FROM Search s2
            WHERE s2.member = :member
            ORDER BY s2.id DESC
            LIMIT :limit
        )
    """)
    void deleteOldSearchesBeyondLimit(@Param("member") Member member, @Param("limit") int limit);

    Optional<Search> findByIdAndMember_Id(Long id, Long memberId);
}