package kr.co.fastcampus.fastcatch.domain.accommodation.controller;

import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodationOption;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoomOption;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.ApiTest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationInfoResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSearchPageResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSummaryResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class AccommodationControllerTest extends ApiTest {

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
    @DisplayName("숙박 전체 조회")
    void getAccommodations() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        String url = "/api/accommodations?category=" + Category.ALL
            + "&region=" + Region.ALL
            + "&startDate=" + startDate
            + "&endDate=" + endDate
            + "&headCount=" + 2;

        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when().get(url)
            .then().log().all()
            .extract();

        JsonPath jsonPath = response.jsonPath();
        String status = jsonPath.getString("status");
        List<AccommodationSummaryResponse> accommodations =
            jsonPath.getList("data.accommodations", AccommodationSummaryResponse.class);
        int totalElements = jsonPath.getInt("data.totalElements");
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(status).isEqualTo("SUCCESS");
            softly.assertThat(accommodations.size()).isEqualTo(1);
            softly.assertThat(accommodations.get(0).address()).isEqualTo("testAddress");
            softly.assertThat(accommodations.get(0).name()).isEqualTo("testHotel");
            softly.assertThat(totalElements).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("숙박 상세 조회")
    void getAccommodationWithRooms() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        String url = "/api/accommodations/1"
            + "?startDate=" + startDate
            + "&endDate=" + endDate;

        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when().get(url)
            .then().log().all()
            .extract();

        JsonPath jsonPath = response.jsonPath();
        String status = jsonPath.getString("status");
        AccommodationInfoResponse result =
            jsonPath.getObject("data", AccommodationInfoResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(status).isEqualTo("SUCCESS");
            softly.assertThat(result.rooms().size()).isEqualTo(2);
            softly.assertThat(result.address()).isEqualTo("testAddress");
            softly.assertThat(result.name()).isEqualTo("testHotel");
            softly.assertThat(result.rooms().get(0).name()).isEqualTo("testRoom");
            softly.assertThat(result.latitude()).isEqualTo("37.5645211757");
            softly.assertThat(result.longitude()).isEqualTo("126.9800598249");
            softly.assertThat(result.rooms().get(1).price()).isEqualTo(45000);
        });
    }

    @Test
    @DisplayName("숙박 이름으로 숙박 검색")
    void getAccommodationByName() {

        String query = "testHotel";

        String url = "/api/accommodations/search-by-name"
            + "?query=" + query;

        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when().get(url)
            .then().log().all()
            .extract();

        JsonPath jsonPath = response.jsonPath();
        String status = jsonPath.getString("status");
        AccommodationSearchPageResponse result =
            jsonPath.getObject("data", AccommodationSearchPageResponse.class);
        int totalElements = jsonPath.getInt("data.totalElements");
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(status).isEqualTo("SUCCESS");
            softly.assertThat(result.accommodations().size()).isEqualTo(1);
            softly.assertThat(result.accommodations().get(0).region())
                .isEqualTo(Region.SEOUL);
            softly.assertThat(result.accommodations().get(0).category())
                .isEqualTo(Category.HOTELRESORT);
            softly.assertThat(totalElements).isEqualTo(1);
        });
    }
}
