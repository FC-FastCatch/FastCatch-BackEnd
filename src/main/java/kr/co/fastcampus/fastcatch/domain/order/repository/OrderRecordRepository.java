package kr.co.fastcampus.fastcatch.domain.order.repository;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import org.springframework.data.repository.CrudRepository;

public interface OrderRecordRepository extends CrudRepository<OrderRecord, Long> {

    long countByAccommodationIdAndStayDate(Long accommodationId, LocalDate stayDate);

    boolean existsByRoomIdAndStayDate(Long roomId, LocalDate stayDate);
}
