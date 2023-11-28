package kr.co.fastcampus.fastcatch.domain.test;

import lombok.Builder;

@Builder
public record InfoResponse(
    Long id,
    String name,
    String email
) {

    public static InfoResponse from(TestEntity testEntity) {
        return InfoResponse.builder()
            .id(testEntity.getId())
            .name(testEntity.getName())
            .email(testEntity.getEmail())
            .build();
    }
}
