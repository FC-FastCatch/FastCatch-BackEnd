package kr.co.fastcampus.fastcatch.domain.cart.controller;

import static kr.co.fastcampus.fastcatch.common.MemberTokenUtil.getAccessToken;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodationOption;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoomOption;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import kr.co.fastcampus.fastcatch.common.ApiTest;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.fastcatch.domain.cart.dto.request.CartItemRequest;
import kr.co.fastcampus.fastcatch.domain.cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CartControllerTest extends ApiTest {

    @Autowired
    CartService cartService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationOptionRepository accommodationOptionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomOptionRepository roomOptionRepository;

    @BeforeEach
    void setUp() {
        Accommodation accommodation = createAccommodation();
        accommodationRepository.save(accommodation);

        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodationOptionRepository.save(accommodationOption);
        accommodation.registerAccommodationOption(accommodationOption);

        Room room = createRoom(accommodation);
        roomRepository.save(room);

        Room room2 = createRoom(accommodation);
        roomRepository.save(room2);

        RoomOption roomOption = createRoomOption(room);
        roomOptionRepository.save(roomOption);
        room.registerRoomOption(roomOption);

        RoomOption roomOption2 = createRoomOption(room2);
        roomOptionRepository.save(roomOption2);
        room2.registerRoomOption(roomOption2);

        accommodation.addRoom(room);
        accommodation.addRoom(room2);
    }
    @Test
    @DisplayName("장바구니 전체 정보 조회")
    void getCart() {
        // given
        String url = "/api/carts";
        String accessToken = getAccessToken();

        // when
        ExtractableResponse<Response> response =
            restAssuredGetWithToken(url, accessToken);

        // then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getList("data.cartItemResponseList")).isEmpty();
    }

    @Test
    @DisplayName("장바구니 아이템 추가")
    void addCartItem() {
        // given
        String url = "/api/carts";
        CartItemRequest request = new CartItemRequest(
            1l,
            LocalDate.of(2024, 11, 11),
            LocalDate.of(2024, 11, 12),
            2,
            5000
        );
        String accessToken = getAccessToken();

        // when
        ExtractableResponse<Response> response =
            restAssuredPostWithToken(url, request, accessToken);

        // then
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getList("data.cartItemResponseList.rooms[0]"))
            .isEqualTo(Arrays.asList(
                new HashMap<String, Object>() {{
                    put("cartItemId", 1);
                    put("roomId", 1);
                    put("roomName", "testRoom");
                    put("startDate", "2024-11-11");
                    put("endDate", "2024-11-12");
                    put("headCount", 2);
                    put("price", 5000);
                    put("checkInTime", "14:00");
                    put("checkOutTime", "12:00");
                    put("maxHeadCount", 4);
                }}
            ));
    }

    @Test
    @DisplayName("장바구니 아이템 전체 삭제")
    void deleteAllCartItem() {
        // given
        String url = "/api/carts";
        CartItemRequest request = new CartItemRequest(
            1l,
            LocalDate.of(2024, 11, 11),
            LocalDate.of(2024, 11, 12),
            2,
            5000
        );
        String accessToken = getAccessToken();
        restAssuredPostWithToken(url, request, accessToken);

        // when
        ExtractableResponse<Response> response =
            restAssuredDeleteWithToken(url, accessToken);
        JsonPath jsonPath = response.jsonPath();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getList("data.cartItemResponseList")).isEmpty();
    }

    @Test
    @DisplayName("장바구니 특정 아이템 삭제")
    void deleteCartItem() {
        // given
        String url = "/api/carts";
        CartItemRequest request1 = new CartItemRequest(
            1l,
            LocalDate.of(2024, 11, 11),
            LocalDate.of(2024, 11, 12),
            2,
            5000
        );
        String accessToken = getAccessToken();
        restAssuredPostWithToken(url, request1, accessToken);

        CartItemRequest request2 = new CartItemRequest(
            2l,
            LocalDate.of(2024, 11, 11),
            LocalDate.of(2024, 11, 12),
            2,
            5000
        );
        restAssuredPostWithToken(url, request2, accessToken);

        url = "/api/cart-items/2";

        // when
        ExtractableResponse<Response> response =
            restAssuredCertatinDeleteWithToken(url, request2, accessToken);

        //then
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getList("data.cartItemResponseList.rooms[0]"))
            .isEqualTo(Arrays.asList(
                new HashMap<String, Object>() {{
                    put("cartItemId", 1);
                    put("roomId", 1);
                    put("roomName", "testRoom");
                    put("startDate", "2024-11-11");
                    put("endDate", "2024-11-12");
                    put("headCount", 2);
                    put("price", 5000);
                    put("checkInTime", "14:00");
                    put("checkOutTime", "12:00");
                    put("maxHeadCount", 4);
                }}
            ));

    }


    private ExtractableResponse<Response> restAssuredPostWithToken(
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
            .post(url)
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

    private ExtractableResponse<Response> restAssuredDeleteWithToken(
        String url,
        String accessToken
    ) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", accessToken)
            .when()
            .delete(url)
            .then().log().all()
            .extract();

    }

    private ExtractableResponse<Response> restAssuredCertatinDeleteWithToken(
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
            .delete(url)
            .then().log().all()
            .extract();

    }
}