package kr.co.fastcampus.fastcatch.domain.init.repository;

import kr.co.fastcampus.fastcatch.domain.init.entity.InitAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitAccommodationRepository extends JpaRepository<InitAccommodation, Long> {

}
