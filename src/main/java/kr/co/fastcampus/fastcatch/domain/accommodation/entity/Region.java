package kr.co.fastcampus.fastcatch.domain.accommodation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
    ALL("전체"),
    SEOUL("서울"),
    GYEONGGI("경기"),
    GANGWON("강원"),
    CHUNGCHEONG("충청"),
    HONAM("호남"),
    GYEONGSANG("경상"),
    JEJU("제주");

    private final String location;

}
