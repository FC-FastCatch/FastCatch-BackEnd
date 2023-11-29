package kr.co.fastcampus.fastcatch.domain.member.dto.response;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberResponse(
    Long id,
    String email,
    String password,
    String name,
    String nickname,
    LocalDate birthday,
    String phoneNumber,
    Long cartId
) {

    public static MemberResponse from(Member member, Long cartId) {
        return MemberResponse.builder()
            .id(member.getMemberId())
            .email(member.getEmail())
            .name(member.getName())
            .nickname(member.getNickname())
            .birthday(member.getBirthday())
            .phoneNumber(member.getPhoneNumber())
            .cartId(cartId)
            .build();
    }
}
