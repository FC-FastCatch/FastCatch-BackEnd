package kr.co.fastcampus.fastcatch.domain.member.service;


import kr.co.fastcampus.fastcatch.common.exception.DuplicatedEmailException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedNicknameException;
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
            throw new DuplicatedEmailException();
        }
        if (memberRepository.existsByNickname(request.nickname())) {
            throw new DuplicatedNicknameException();
        }
        Member member = memberRepository.save(request.toEntity(passwordEncoder));

        return MemberSignupResponse.from(member);
    }
}





