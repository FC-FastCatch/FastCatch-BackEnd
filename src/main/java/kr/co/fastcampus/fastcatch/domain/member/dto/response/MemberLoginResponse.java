package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import jakarta.validation.constraints.NotBlank;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.Builder;
import java.util.UUID;

@Builder
public record MemberLoginResponse(
    @NotBlank(message = "이메일")
    String email,
    String accessToken
) {

    public static MemberLoginResponse from(Member member) {
        String accessToken = generatedAccessToken(member);
        return MemberLoginResponse.builder()
            .email(member.getEmail())
            .accessToken(accessToken)
            .build();
    }

    private static String generatedAccessToken(Member member) {
        return UUID.randomUUID().toString();
    }
}
