package kr.co.fastcampus.fastcatch.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReIssueTokenRequest(
    @NotBlank(message = "회원 이메일을 필수로 입력하셔야 합니다.")
    String email,
    @NotBlank(message = "리프레시 토큰을 필수로 입력하셔야 합니다.")
    String refreshToken
) {

}
