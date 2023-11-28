package kr.co.fastcampus.fastcatch.domain.member.repository;

import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
