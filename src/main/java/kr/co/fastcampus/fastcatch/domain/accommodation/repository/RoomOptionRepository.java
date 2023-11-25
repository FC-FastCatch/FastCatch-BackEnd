package kr.co.fastcampus.fastcatch.domain.accommodation.repository;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import org.springframework.data.repository.CrudRepository;

public interface RoomOptionRepository extends CrudRepository<RoomOption, Long> {
    boolean existsByRoomId(Long roomId);
}
