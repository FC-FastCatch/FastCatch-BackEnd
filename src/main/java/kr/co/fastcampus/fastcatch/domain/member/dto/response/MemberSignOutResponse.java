package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import lombok.Builder;
@Builder
public record MemberSignOutResponse(
    Long blackListId,
    Long memberId,
    String email,
    String accessToken,
    String refreshToken
) {

}
