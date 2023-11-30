package kr.co.fastcampus.fastcatch.domain.member.controller;

import static kr.co.fastcampus.fastcatch.common.MemberTokenUtil.getAccessToken;
import static kr.co.fastcampus.fastcatch.common.MemberTokenUtil.restAssuredPostBody;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.ApiTest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberUpdateRequest;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberControllerTest extends ApiTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    void addMember() {

        // given
        String url = "/api/members/signup";
        MemberSignupRequest request = new MemberSignupRequest(
            "email@email.com",
            "tsetst123",
            "name",
            "nickname",
            LocalDate.of(2020, 11, 11),
            "0103235656"
        );

        // when
        ExtractableResponse<Response> response = restAssuredPostBody(url, request);

        // then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getLong("data.id")).isEqualTo(1L);
    }

    @Test
    @DisplayName("내정보 수정")
    void modifyMemberInfo() {

        // given
        String url = "/api/members";
        MemberUpdateRequest request = new MemberUpdateRequest(
            "changeName",
            "changeNickname",
            LocalDate.of(2020, 11, 11),
            "0103235656"
        );
        String accessToken = getAccessToken();

        // when
        ExtractableResponse<Response> response =
            restAssuredPutWithToken(url, request, accessToken);

        // then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getLong("data.id")).isEqualTo(1L);
        assertThat(jsonPath.getString("data.name")).isEqualTo("changeName");
        assertThat(jsonPath.getString("data.nickname")).isEqualTo("changeNickname");

    }

    @Test
    @DisplayName("내정보 조회")
    void getMemberInfo() {

        // given
        String url = "/api/members";
        String accessToken = getAccessToken();

        // when
        ExtractableResponse<Response> response =
            restAssuredGetWithToken(url, accessToken);

        // then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getLong("data.id")).isEqualTo(1L);
        assertThat(jsonPath.getString("data.name")).isEqualTo("name");
        assertThat(jsonPath.getString("data.nickname")).isEqualTo("nickname");

    }

    private ExtractableResponse<Response> restAssuredPutWithToken(
        String url,
        Object request,
        String accessToken
    ) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", accessToken)
            .body(request)
            .when()
            .put(url)
            .then().log().all()
            .extract();

    }

    private ExtractableResponse<Response> restAssuredGetWithToken(
        String url,
        String accessToken
    ) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", accessToken)
            .when()
            .get(url)
            .then().log().all()
            .extract();

    }
}
