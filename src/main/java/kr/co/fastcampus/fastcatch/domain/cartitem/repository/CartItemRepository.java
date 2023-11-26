package kr.co.fastcampus.fastcatch.domain.cartitem.repository;

import kr.co.fastcampus.fastcatch.domain.cartitem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
