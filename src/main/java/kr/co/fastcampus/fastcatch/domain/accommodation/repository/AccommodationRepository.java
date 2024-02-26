package kr.co.fastcampus.fastcatch.domain.accommodation.repository;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("SELECT a FROM Accommodation a WHERE "
        + "a.category = COALESCE(:category, a.category) "
        + "AND a.region = COALESCE(:region, a.region) "
        + "AND a.maximumCapacity >= :headCount")
    Page<Accommodation> findAccommodations(
        @Param("category") Category category,
        @Param("region") Region region,
        @Param("headCount") Integer headCount,
        Pageable pageable
    );

    Page<Accommodation> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
