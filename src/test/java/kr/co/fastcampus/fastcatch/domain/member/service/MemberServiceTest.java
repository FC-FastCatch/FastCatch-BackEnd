package kr.co.fastcampus.fastcatch.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedEmailException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedNicknameException;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberResponse;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    void createMember() {
        // given
        MemberSignupRequest dto = createMemberSignupRequest();
        Member member = Member.builder()
            .email(dto.email())
            .name(dto.name())
            .nickname(dto.nickname())
            .password(passwordEncoder.encode(dto.password()))
            .birthday(dto.birthday())
            .phoneNumber(dto.phoneNumber())
            .build();

        String encodePassword = "encodedPassword";

        given(memberRepository.existsByEmail(dto.email()))
            .willReturn(false);
        given(memberRepository.existsByNickname(dto.nickname()))
            .willReturn(false);
        given(passwordEncoder.encode(dto.password()))
            .willReturn(encodePassword);
        given(memberRepository.save(any(Member.class)))
            .willReturn(member);
        given((cartRepository.save(any(Cart.class))))
            .willReturn(Cart.createCart(member));

        // when
        MemberResponse result = memberService.createMember(dto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(dto.email());
        assertThat(result.name()).isEqualTo(dto.name());
        assertThat(result.nickname()).isEqualTo(dto.nickname());
        verify(memberRepository, atLeastOnce()).save(any(Member.class));
    }

    @Test
    @DisplayName("이메일 중복으로 회원가입 실패")
    void createMember_fail() {
        // given
        MemberSignupRequest dto = createMemberSignupRequest();

        given(memberRepository.existsByEmail(dto.email()))
            .willReturn(dto.email().equals("email"));

        // when
        // then
        assertThatThrownBy(() -> memberService.createMember(dto))
            .isInstanceOf(DuplicatedEmailException.class)
            .hasMessage(new DuplicatedEmailException().getMessage());
    }

    @Test
    @DisplayName("닉네임 중복으로 회원가입 실패")
    void createMember_fail_by_nickname() {
        // given
        MemberSignupRequest dto = createMemberSignupRequest();

        given(memberRepository.existsByNickname(dto.nickname()))
            .willReturn(dto.nickname().equals("nickname"));

        // when
        // then
        assertThatThrownBy(() -> memberService.createMember(dto))
            .isInstanceOf(DuplicatedNicknameException.class)
            .hasMessage(new DuplicatedNicknameException().getMessage());
    }

    private MemberSignupRequest createMemberSignupRequest() {
        return new MemberSignupRequest(
            "email",
            "tsetst123",
            "name",
            "nickname",
            LocalDate.of(2020, 11, 11),
            "0103235656"
        );
    }
}
