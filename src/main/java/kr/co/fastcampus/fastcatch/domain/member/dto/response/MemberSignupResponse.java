package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record MemberSignupResponse(
    Long id,
    @NotBlank(message = "이메일을 필수로 입력하셔야 합니다.")
    String email,
    @NotBlank(message = "비밀번호를 필수로 입력하셔야 합니다.")
    String password,
    @NotNull(message = "이름을 필수로 입력하셔야 합니다.")
    String name,
    @NotBlank(message = "닉네임을 필수로 입력하셔야 합니다.")
    String nickname,
    @NotBlank(message = "생일을 필수로 입력하셔야 합니다.")
    LocalDate birthday,
    @NotBlank(message = "핸드폰 번호를 필수로 입력하셔야 합니다.")
    String phoneNumber
) {
    public static MemberSignupResponse from(Member member) {
        return MemberSignupResponse.builder()
            .id(member.getMemberId())
            .email(member.getEmail())
            .name(member.getName())
            .nickname(member.getNickname())
            .birthday(member.getBirthday())
            .phoneNumber(member.getPhoneNumber())
            .build();
    }
}
