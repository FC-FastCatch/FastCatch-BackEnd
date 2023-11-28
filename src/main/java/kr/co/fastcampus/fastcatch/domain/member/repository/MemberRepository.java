package kr.co.fastcampus.fastcatch.domain.member.repository;

import java.util.Optional;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}
