package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
    String accessToken
) {

}
