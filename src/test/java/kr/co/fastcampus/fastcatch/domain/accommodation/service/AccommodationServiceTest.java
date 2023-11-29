package kr.co.fastcampus.fastcatch.domain.accommodation.service;

import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationPageResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSummaryResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodationOption;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoomOption;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertFalse;

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
    @DisplayName("")
    void findAccommodationsWithSoldOutCheck() {
        //given
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        Pageable pageable = PageRequest.of(0, 10);
        Accommodation accommodation = createAccommodation();
        Page<Accommodation>
        accommodationRepository.save(accommodation);
        accommodationOptionRepository.save(createAccommodationOption(accommodation));
        Room room = createRoom(accommodation);
        roomRepository.save(room);
        roomOptionRepository.save(createRoomOption(room));
        //given(accommodationRepository.findAccommodationsByHeadCount(1,pageable)).willReturn(accommodation)
        AccommodationPageResponse response = accommodationService.findAccommodationsWithSoldOutCheck(
            Category.ALL, Region.ALL, startDate, endDate, 1, pageable
        );


        response.accommodations().forEach(this::assertAccommodationSummaryResponse);
    }

    private void assertAccommodationSummaryResponse(AccommodationSummaryResponse accommodationSummaryResponse) {
        assertNotNull(accommodationSummaryResponse.id());
        assertNotNull(accommodationSummaryResponse.name());
        assertThat(accommodationSummaryResponse.soldOut()).isEqualTo(false);
    }

}
