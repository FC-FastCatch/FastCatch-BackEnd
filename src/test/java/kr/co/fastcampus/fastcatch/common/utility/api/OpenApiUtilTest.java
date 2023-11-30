package kr.co.fastcampus.fastcatch.common.utility.api;

import java.util.List;
import kr.co.fastcampus.fastcatch.common.utility.api.dto.response.OpenApiResponseDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenApiUtilTest {
    @Test
    void requestOpenApi() throws ParseException {
        List<OpenApiResponseDto> openApiResponseDtoList = OpenApiUtil.requestOpenApi();
        for (OpenApiResponseDto openApiresponseDto:openApiResponseDtoList) {
            System.out.println(openApiresponseDto);
        }
    }
}