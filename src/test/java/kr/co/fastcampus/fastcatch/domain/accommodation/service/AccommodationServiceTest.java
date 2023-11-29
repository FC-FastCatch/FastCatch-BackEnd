package kr.co.fastcampus.fastcatch.domain.accommodation.service;

import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodationOption;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoomOption;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.exception.AccommodationNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidDateRangeException;
import kr.co.fastcampus.fastcatch.common.exception.PastDateException;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationInfoResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSearchPageResponse;
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
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private AccommodationOptionRepository accommodationOptionRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomOptionRepository roomOptionRepository;

    @Mock
    private OrderRecordRepository orderRecordRepository;

    @InjectMocks
    private AccommodationService accommodationService;

    @Test
    @DisplayName("숙박 종료 날짜가 시작 날짜보다 앞설 때 예외처리")
    void findAccommodationsWithSoldOutCheck_invalidDateRange_shouldThrowException() {

        //given
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().minusDays(1);
        Pageable pageable = PageRequest.of(0, 10);

        //when, then
        assertThrows(InvalidDateRangeException.class, () -> {
            accommodationService.findAccommodationsWithSoldOutCheck(
                Category.ALL, Region.ALL, startDate, endDate, 1, pageable
            );
        });
    }

    @Test
    @DisplayName("숙박 시작날짜가 오늘 날짜보다 더 과거일 때 예외처리")
    void findAccommodationsWithSoldOutCheck_pastDate_shouldThrowException() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(PastDateException.class, () -> {
            accommodationService.findAccommodationsWithSoldOutCheck(
                Category.ALL, Region.ALL, startDate, endDate, 1, pageable
            );
        });
    }

    @Test
    @DisplayName("숙박 상세 조회 성공")
    void findAccommodationWithRooms() {

        //given
        Long accommodationId = 1L;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);

        Accommodation accommodation = createAccommodation();
        AccommodationOption accommodationOption = createAccommodationOption(accommodation);
        accommodation.registerAccommodationOption(accommodationOption);
        Room room = createRoom(accommodation);
        Room room2 = createRoom(accommodation);
        RoomOption roomOption = createRoomOption(room);
        RoomOption roomOption2 = createRoomOption(room2);
        room.registerRoomOption(roomOption);
        room2.registerRoomOption(roomOption2);
        accommodation.addRoom(room);
        accommodation.addRoom(room2);
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        //when
        AccommodationInfoResponse result = accommodationService.findAccommodationWithRooms(accommodationId, startDate, endDate);

        //then
        assertNotNull(result);
        assertThat(result.name()).isEqualTo(accommodation.getName());
        assertThat(result.rooms().size()).isEqualTo(accommodation.getRooms().size());

    }

    @Test
    @DisplayName("숙박 상세 조회 실패")
    void findAccommodationWithRooms_fail() {

        //given
        Long accommodationId = 1L;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> accommodationService.findAccommodationWithRooms(accommodationId, startDate, endDate))
            .isInstanceOf(AccommodationNotFoundException.class);

    }

    @Test
    @DisplayName("숙박 이름으로 검색 없을시")
    void findAccommodationByName() {

        //given
        Pageable pageable = PageRequest.of(0, 10);

        //when
        when(accommodationRepository.findAllByNameContainingIgnoreCase("testHotel", pageable))
            .thenReturn(new PageImpl<>(new ArrayList<>()));

        AccommodationSearchPageResponse result = accommodationService.findAccommodationByName("testHotel", pageable);

        //then
        assertThat(result.accommodations().size()).isEqualTo(0);
        assertThat(result.totalElements()).isEqualTo(0);
        assertThat(result.isFirst()).isEqualTo(result.isLast());
    }

}
