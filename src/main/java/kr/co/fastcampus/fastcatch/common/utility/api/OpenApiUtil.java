package kr.co.fastcampus.fastcatch.common.utility.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.utility.api.dto.response.OpenApiResponseDto;
import kr.co.fastcampus.fastcatch.common.utility.api.dto.response.OpenApiResponseListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


public class OpenApiUtil {
    private static String baseUrl;
    private static String serviceKey;

    @Value("${spring.open-api.base-url}")
    private void setBaseUrl(String baseUrl) {
        OpenApiUtil.baseUrl = baseUrl;
    }

    @Value("${spring.open-api.key}")
    private void setkey(String serviceKey) {
        OpenApiUtil.serviceKey = serviceKey;
    }

    public static List<OpenApiResponseDto> requestOpenApi() {
        URI uri = uriBuilder();

        RestTemplate restTemplate = new RestTemplate();

        return Optional.ofNullable(
                restTemplate.exchange(uri, HttpMethod.GET, null, OpenApiResponseListDto.class)
                    .getBody()
            )
            .get().item()
            .stream().map(OpenApiResponseDto::from)
            .toList();
    }

    private static URI uriBuilder() {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
            .queryParam("numOfRows", 100)
            .queryParam("pageNo", 1)
            .queryParam("MobileOS", "ETC")
            .queryParam("MobileApp", "FastCatch")
            .queryParam("_type", "json")
            .queryParam("listYN", "Y")
            .queryParam("serviceKey", serviceKey)
            .build()
            .toUri();
    }

}

