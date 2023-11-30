package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSigninResponse(
    String accessToken,
    String refreshToken,
    MemberResponse memberResponse
) {

    @Builder
    public static MemberSigninResponse from(
        String accessToken, String refreshToken, MemberResponse memberResponse
    ) {
        return MemberSigninResponse.builder()
            .accessToken(accessToken).refreshToken(refreshToken)
            .memberResponse(memberResponse)
            .build();
    }
}
