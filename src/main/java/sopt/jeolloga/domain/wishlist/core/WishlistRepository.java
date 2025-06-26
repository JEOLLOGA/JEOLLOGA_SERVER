package sopt.jeolloga.domain.wishlist.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.wishlist.Wishlist;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT w.templestay.id FROM Wishlist w WHERE w.member.id = :memberId")
    List<Long> findTemplestayIdsByMemberId(Long memberId);
}
