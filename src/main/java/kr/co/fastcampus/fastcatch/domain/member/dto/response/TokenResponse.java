package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import lombok.Builder;

public record TokenResponse(
    String accessToken
) {

    @Builder
    public static TokenResponse from(String accessToken) {
        return TokenResponse.builder().accessToken(accessToken).build();
    }
}
