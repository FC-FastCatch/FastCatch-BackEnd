package kr.co.fastcampus.fastcatch.domain.member.service;

import kr.co.fastcampus.fastcatch.common.exception.EntityNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.RoomNotFoundException;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            //exception 나중에 바꾼다
            .orElseThrow(()->new EntityNotFoundException());
    }
}
