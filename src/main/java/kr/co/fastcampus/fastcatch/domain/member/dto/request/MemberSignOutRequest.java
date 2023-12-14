package kr.co.fastcampus.fastcatch.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberSignOutRequest(
    @NotBlank(message = "액세스 토큰을 필수로 입력하셔야 합니다.")
    String accessToken,
    @NotBlank(message = "리프레시 토큰을 필수로 입력하셔야 합니다.")
    String refreshToken
) {

}
