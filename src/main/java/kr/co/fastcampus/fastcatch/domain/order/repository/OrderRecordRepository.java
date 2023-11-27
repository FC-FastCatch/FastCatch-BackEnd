package kr.co.fastcampus.fastcatch.domain.order.repository;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {

    void deleteByOrder(Order order);

    long countByAccommodationIdAndStayDate(Long accommodationId, LocalDate stayDate);

    boolean existsByRoomIdAndStayDate(Long roomId, LocalDate stayDate);
}
