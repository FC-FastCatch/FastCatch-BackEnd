package kr.co.fastcampus.fastcatch.domain.order.repository;

import java.util.List;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderOrderId(Long orderId);
}
