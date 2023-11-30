package kr.co.fastcampus.fastcatch.common;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSigninRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import org.springframework.http.MediaType;

public class MemberTokenUtil {

    public static final String GRANT_TYPE = "Bearer ";

    private MemberTokenUtil() {
    }

    public static String getAccessToken() {
        signupMember();
        String url = "/api/members/signin";
        MemberSigninRequest request =
            new MemberSigninRequest("email@naver.com", "test123456");
        return GRANT_TYPE + restAssuredPostBody(url, request)
            .jsonPath()
            .getString("data.accessToken");
    }

    public static String getAccessToken(MemberSigninRequest request) {
        String url = "/api/members/signin";
        return GRANT_TYPE + restAssuredPostBody(url, request)
            .jsonPath()
            .getString("data.accessToken");
    }

    private static void signupMember() {
        String url = "/api/members/signup";
        MemberSignupRequest request = new MemberSignupRequest(
            "email@naver.com",
            "test123456",
            "name",
            "nickname",
            LocalDate.of(2020, 11, 11),
            "0103235656"
        );
        restAssuredPostBody(url, request);
    }

    public static ExtractableResponse<Response> restAssuredPostBody(String url, Object request) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post(url)
            .then().log().all()
            .extract();
    }
}
