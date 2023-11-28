package kr.co.fastcampus.fastcatch.domain.order.service;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRecordRepository orderRecordRepository;

    public long countAccommodationAtDate(Long accommodationId, LocalDate stayDate) {
        return orderRecordRepository.countByAccommodationIdAndStayDate(
            accommodationId, stayDate
        );
    }

    public boolean existsByRoomIdOnDate(Long roomId, LocalDate stayDate) {
        return orderRecordRepository.existsByRoomIdAndStayDate(
            roomId, stayDate
        );
    }
}
