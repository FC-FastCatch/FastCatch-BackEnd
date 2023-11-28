package kr.co.fastcampus.fastcatch.domain.member.service;


import kr.co.fastcampus.fastcatch.common.exception.DuplicateEmailException;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSignupResponse;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.passwordencoder.PasswordEncoder;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberSignupResponse createMember(MemberSignupRequest request) {

        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }
        Member member = memberRepository.save(request.toEntity(passwordEncoder));

        return MemberSignupResponse.from(member);
    }
}





