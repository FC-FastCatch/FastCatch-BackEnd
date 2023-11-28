package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberSignupResponse(
    Long id,
    String email,
    String password,
    String name,
    String nickname,
    LocalDate birthday,
    String phoneNumber
) {

    public static MemberSignupResponse from(Member member) {
        return MemberSignupResponse.builder()
            .id(member.getId())
            .email(member.getEmail())
            .name(member.getName())
            .nickname(member.getNickname())
            .birthday(member.getBirthday())
            .phoneNumber(member.getPhoneNumber())
            .build();
    }
}
