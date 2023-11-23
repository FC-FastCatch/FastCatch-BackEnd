package kr.co.fastcampus.fastcatch.domain.order.repository;

import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
