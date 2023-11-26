package kr.co.fastcampus.fastcatch.domain.cart.repository;

import java.util.Optional;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.member.memberId = :memberId")
    Optional<Cart> findByMemberId(@Param("memberId") Long memberId);
}
