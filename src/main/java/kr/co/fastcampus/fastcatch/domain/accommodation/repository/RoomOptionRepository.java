package kr.co.fastcampus.fastcatch.domain.accommodation.repository;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomOptionRepository extends JpaRepository<RoomOption, Long> {
    boolean existsByRoomId(Long roomId);
}
