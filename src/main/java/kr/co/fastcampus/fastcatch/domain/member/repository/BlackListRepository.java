package kr.co.fastcampus.fastcatch.domain.member.repository;

import kr.co.fastcampus.fastcatch.domain.member.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsByAccessToken(String accessToken);
    boolean existsByRefreshToken(String refreshToken);
}
