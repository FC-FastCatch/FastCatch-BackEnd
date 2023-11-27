package kr.co.fastcampus.fastcatch.domain.member.service;


import kr.co.fastcampus.fastcatch.common.exception.DuplicateEmailException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidPasswordException;
import kr.co.fastcampus.fastcatch.common.exception.MemberNotFoundException;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberLoginRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberLoginResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSignupResponse;
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

    @Transactional
    public MemberLoginResponse createLogin(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(MemberNotFoundException::new);
        if(bCryptPasswordEncoder.checkPassword(request.password(), member.getPassword())){
            return MemberLoginResponse.from(member);
        } else {
            throw new InvalidPasswordException();
        }
    }
}





