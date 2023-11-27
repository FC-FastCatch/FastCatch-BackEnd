package kr.co.fastcampus.fastcatch.domain.accommodation.repository;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationOptionRepository extends JpaRepository<AccommodationOption, Long> {

    boolean existsByAccommodationId(Long accommodationId);
}
