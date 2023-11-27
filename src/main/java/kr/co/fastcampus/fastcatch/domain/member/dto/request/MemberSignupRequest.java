package kr.co.fastcampus.fastcatch.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.passwordencoder.BCryptPasswordEncoder;
import java.time.LocalDate;

public record MemberSignupRequest(
    @NotBlank(message = "이메일을 필수로 입력하셔야 합니다.")
    String email,
    @NotBlank(message = "비밀번호를 필수로 입력하셔야 합니다.")
    String password,
    @NotNull(message = "이름을 필수로 입력하셔야 합니다.")
    String name,
    @NotBlank(message = "닉네임을 필수로 입력하셔야 합니다.")
    String nickname,
    @NotNull(message = "생일을 필수로 입력하셔야 합니다.")
    LocalDate birthday,
    @NotBlank(message = "핸드폰 번호를 필수로 입력하셔야 합니다.")
    String phoneNumber
) {

    public Member toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
            .email(email)
            .password(bCryptPasswordEncoder.hashPassword(password))
            .name(name)
            .nickname(nickname)
            .birthday(birthday)
            .phoneNumber(phoneNumber)
            .build();
    }
}
