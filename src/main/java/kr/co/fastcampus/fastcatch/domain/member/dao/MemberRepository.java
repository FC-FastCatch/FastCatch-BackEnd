package kr.co.fastcampus.fastcatch.domain.member.dao;

import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
