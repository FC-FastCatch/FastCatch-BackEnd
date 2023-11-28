package kr.co.fastcampus.fastcatch.domain.member.service;


import kr.co.fastcampus.fastcatch.common.exception.DuplicateEmailException;
import kr.co.fastcampus.fastcatch.domain.member.dto.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.MemberSignupResponse;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.passwordencoder.BCryptPasswordEncoder;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public MemberSignupResponse createMember(MemberSignupRequest request) {

        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }
        Member member = memberRepository.save(request.toEntity(bCryptPasswordEncoder));

        return MemberSignupResponse.from(member);
    }
}





