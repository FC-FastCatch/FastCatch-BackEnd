package kr.co.fastcampus.fastcatch.common.utility.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record OpenApiResponseListDto(
    @JsonProperty("item")
    List<OpenApiResponseDto> item
) {
}
