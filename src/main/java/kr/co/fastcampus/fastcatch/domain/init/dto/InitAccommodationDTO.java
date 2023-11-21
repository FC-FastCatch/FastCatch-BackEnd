package kr.co.fastcampus.fastcatch.domain.init.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//json이랑 같이 카멜 변수로 바꿔도 에러 생김 그냥 스네이크 쓰는 걸로
public class InitAccommodationDTO {

    private String name;
    private String address;
    private String address_short;
    private int category;
    private String description;

}
