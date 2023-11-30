package kr.co.fastcampus.fastcatch.domain.order.controller;

import static kr.co.fastcampus.fastcatch.common.MemberTokenUtil.getAccessToken;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodationOption;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createMember;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrder;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderItem;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderItemRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoomOption;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import kr.co.fastcampus.fastcatch.common.ApiTest;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class OrderControllerTest extends ApiTest {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationOptionRepository accommodationOptionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomOptionRepository roomOptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Autowired
    private MemberRepository memberRepository;


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

        Member member = createMember(2L);
        memberRepository.save(member);

        Order order = createOrder(member, 1L);
        orderRepository.save(order);
        OrderItem orderItem = createOrderItem(room, order);
        orderItemRepository.save(orderItem);
        OrderRecord orderRecord = OrderRecord.builder().order(order).accommodation(accommodation)
            .room(room).stayDate(
                LocalDate.of(2023, 11, 30)).build();
        orderRecordRepository.save(orderRecord);
        order.getOrderItems().add(orderItem);

    }

    @Test
    @DisplayName("주문 생성")
    void addOrder() {

        //given
        String url = "/api/orders";
        String accessToken = getAccessToken();

        OrderItemRequest orderItemRequest = createOrderItemRequest();
        OrderRequest orderRequest = createOrderRequest(List.of(orderItemRequest));

        //when
        ExtractableResponse<Response> response = restAssuredPostWithToken(url, orderRequest,
            accessToken);

        //then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getLong("data.orderId")).isEqualTo(2L);
    }

    @Test
    @DisplayName("주문 목록 조회")
    void getOrders() {

        //given
        String url = "/api/orders";
        String accessToken = getAccessToken();

        //when
        ExtractableResponse<Response> response = restAssuredGetListWithToken(url, accessToken);

        //then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getList("data.orders")).isNotNull();
    }

    @Test
    @DisplayName("특정 상태의 주문 목록 조회")
    void getOrdersByOrderStatus() {

        //given
        String url = "/api/orders/status/{status}";
        Map<String, ?> pathParams = Map.of("status", "reserved");
        Map<String, ?> queryParams = Map.of("page", 1);

        String accessToken = getAccessToken();

        //when
        ExtractableResponse<Response> response = restAssuredGetWithToken(url, pathParams,
            queryParams, accessToken);

        //then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(jsonPath.getInt("data.pageSize")).isEqualTo(3);
        assertThat(jsonPath.getList("data.orderResponses")).isNotNull();
    }

    @Test
    @DisplayName("주문 취소 접근 권한 예외")
    void deleteOrder() {

        //given
        String url = "/api/orders/{orderId}";
        Long pathVariableValue = 1L;
        Map<String, ?> pathParams = Map.of("orderId", pathVariableValue);

        String accessToken = getAccessToken();

        //when
        ExtractableResponse<Response> response = restAssuredDeleteWithToken(url, pathParams,
            accessToken);

        //then
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> restAssuredPostWithToken(
        String url,
        OrderRequest request,
        String accessToken) {
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


    private ExtractableResponse<Response> restAssuredGetListWithToken(
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

    private ExtractableResponse<Response> restAssuredGetWithToken(
        String url,
        Map<String, ?> pathParams,
        Map<String, ?> queryParams,
        String accessToken
    ) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", accessToken)
            .pathParams(pathParams)
            .queryParams(queryParams)
            .when()
            .get(url)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> restAssuredDeleteWithToken(String url,
        Map<String, ?> pathParams, String accessToken) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", accessToken)
            .pathParams(pathParams)
            .when()
            .delete(url)
            .then().log().all()
            .extract();
    }
}
