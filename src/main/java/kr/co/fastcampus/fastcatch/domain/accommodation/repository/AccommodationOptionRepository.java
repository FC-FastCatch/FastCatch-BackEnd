package kr.co.fastcampus.fastcatch.domain.accommodation.repository;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import org.springframework.data.repository.CrudRepository;

public interface AccommodationOptionRepository extends CrudRepository<AccommodationOption, Long> {

    boolean existsByAccommodationId(Long accommodationId);
}
