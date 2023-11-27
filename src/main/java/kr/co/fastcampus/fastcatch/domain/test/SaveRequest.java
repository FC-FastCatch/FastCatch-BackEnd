package kr.co.fastcampus.fastcatch.domain.test;

import lombok.Builder;

@Builder
public record SaveRequest(
    String name,
    String email
) {

    public TestEntity toEntity() {
        return TestEntity.builder()
            .name(name)
            .email(email)
            .build();
    }
}
