package kr.co.fastcampus.fastcatch.common.utility.api.dto.response;

import lombok.Builder;

@Builder
public record OpenApiResponseDto(
    String addr1,
    String areacode,
    String firstimage,
    String mapx,
    String mapy,
    String lot,
    String lat,
    String tel,
    String title
) {
    public static OpenApiResponseDto from(
        OpenApiResponseDto openApiResponseDto
    ) {
        return OpenApiResponseDto.builder()
            .addr1(openApiResponseDto.addr1)
            .areacode(openApiResponseDto.areacode)
            .firstimage(openApiResponseDto.firstimage)
            .mapx(openApiResponseDto.mapx)
            .mapy(openApiResponseDto.mapy)
            .lot(openApiResponseDto.lot)
            .lat(openApiResponseDto.lat)
            .tel(openApiResponseDto.tel)
            .title(openApiResponseDto.title)
            .build();
    }
}
