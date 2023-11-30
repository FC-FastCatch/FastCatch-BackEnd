package kr.co.fastcampus.fastcatch.common.utility.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.utility.api.dto.response.OpenApiResponseDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class OpenApiUtil {
    private static String baseUrl = "http://openapi.seoul.go.kr:8088";
    private static String serviceKey = "5756515a6a76696b38347645497154";

    @Value("${spring.open-api.base-url}")
    private void setBaseUrl(String baseUrl) {
        OpenApiUtil.baseUrl = baseUrl;
    }

    @Value("${spring.open-api.key}")
    private void setkey(String serviceKey) {
        OpenApiUtil.serviceKey = serviceKey;
    }

    public static List<OpenApiResponseDto> requestOpenApi() throws ParseException {
        URI uri = uriBuilder(1, 100);
        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(uri, String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONObject jsonSebcHotelListKor = (JSONObject) jsonObject.get("SebcHotelListKor");
        JSONArray jsonRowList = (JSONArray) jsonSebcHotelListKor.get("row");

        List<OpenApiResponseDto> openApiResponseDtoList = new ArrayList<>();

        for (Object object : jsonRowList) {
            JSONObject row = (JSONObject) object;
            openApiResponseDtoList.add(OpenApiResponseDto.fromJson(row));
        }

        return openApiResponseDtoList;

    }

    private static URI uriBuilder(int page, int count) {
        return UriComponentsBuilder.fromUriString(baseUrl + "/" + serviceKey)
            .path("/json/SebcHotelListKor/{page}/{count}")
            .buildAndExpand(page, count)
            .toUri();
    }

}

