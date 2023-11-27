package kr.co.fastcampus.fastcatch.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
    @NotBlank(message = "이메일을 필수로 입력하셔야 합니다.")
    String email,
    @NotBlank(message = "비밀번호를 필수로 입력하셔야 합니다.")
    String password
) {
}
