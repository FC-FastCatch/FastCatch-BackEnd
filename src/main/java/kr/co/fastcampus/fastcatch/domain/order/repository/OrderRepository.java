package kr.co.fastcampus.fastcatch.domain.order.repository;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.member = :member AND o.orderStatus = :orderStatus "
        + "AND (SELECT MIN(oi.startDate) FROM OrderItem oi WHERE oi.order = o) >= :today ")
    Page<Order> findOrdersReserved(
        @Param("member") Member member,
        @Param("orderStatus") OrderStatus orderStatus,
        @Param("today") LocalDate today,
        Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.member = :member AND o.orderStatus = :orderStatus "
        + "AND (SELECT MIN(oi.startDate) FROM OrderItem oi WHERE oi.order = o) < :today ")
    Page<Order> findOrdersUsed(
        @Param("member") Member member,
        @Param("orderStatus") OrderStatus orderStatus,
        @Param("today") LocalDate today,
        Pageable pageable);

    Page<Order> findByMemberAndOrderStatusOrderByCreatedDateDesc(
        Member member,
        OrderStatus orderStatus,
        Pageable pageable);
}
