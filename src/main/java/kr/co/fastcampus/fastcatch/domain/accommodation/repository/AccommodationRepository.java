package kr.co.fastcampus.fastcatch.domain.accommodation.repository;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {

    @Query("SELECT a FROM Accommodation a " +
        "WHERE a.category = :category " +
        "AND a.region = :region " +
        "AND a.maximumCapacity >= :headCount")
    Page<Accommodation> findAccommodationsByCategoryAndRegionAndHeadCount(
        @Param("category") Category category,
        @Param("region") Region region,
        @Param("headCount") Integer headCount,
        Pageable pageable
    );

    @Query("SELECT a FROM Accommodation a " +
        "WHERE a.category = :category " +
        "AND a.maximumCapacity >= :headCount")
    Page<Accommodation> findAccommodationsByCategoryAndHeadCount(
        @Param("category") Category category,
        @Param("headCount") Integer headCount,
        Pageable pageable
    );

    @Query("SELECT a FROM Accommodation a " +
        "WHERE a.region = :region " +
        "AND a.maximumCapacity >= :headCount")
    Page<Accommodation> findAccommodationsByRegionAndHeadCount(
        @Param("region") Region region,
        @Param("headCount") Integer headCount,
        Pageable pageable
    );

    @Query("SELECT a FROM Accommodation a " +
        "WHERE a.maximumCapacity >= :headCount ")
    Page<Accommodation> findAccommodationsByHeadCount(
        @Param("headCount") Integer headCount,
        Pageable pageable
    );

    Page<Accommodation> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
