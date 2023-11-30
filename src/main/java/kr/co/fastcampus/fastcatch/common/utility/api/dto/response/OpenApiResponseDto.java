package kr.co.fastcampus.fastcatch.common.utility.api.dto.response;

import lombok.Builder;
import org.json.simple.JSONObject;

@Builder
public record OpenApiResponseDto(
    String mainKey,
    String category,
    String name,
    String address
) {
    public static OpenApiResponseDto fromJson(JSONObject row) {
        return OpenApiResponseDto
            .builder()
            .mainKey((String) row.get("MAIN_KEY"))
            .category((String) row.get("CATE3_NAME"))
            .name((String) row.get("NAME_KOR"))
            .address((String) row.get("H_KOR_CITY") + " " + row.get("H_KOR_GU") + " " +
                row.get("H_KOR_DONG"))
            .build();
    }
}
