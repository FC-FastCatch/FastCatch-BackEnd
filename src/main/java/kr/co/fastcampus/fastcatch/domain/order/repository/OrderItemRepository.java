package kr.co.fastcampus.fastcatch.domain.order.repository;

import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
