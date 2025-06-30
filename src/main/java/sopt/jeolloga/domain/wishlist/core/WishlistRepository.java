package sopt.jeolloga.domain.wishlist.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.wishlist.Wishlist;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT w.templestay.id FROM Wishlist w WHERE w.member.id = :memberId")
    List<Long> findTemplestayIdsByMemberId(Long memberId);
    boolean existsByMember_IdAndTemplestay_Id(Long memberId, Long templestayId);

    Optional<Wishlist> findByMemberAndTemplestay(Member member, Templestay templestay);
}
