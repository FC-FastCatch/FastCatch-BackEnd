package kr.co.fastcampus.fastcatch.domain.accommodation.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kr.co.fastcampus.fastcatch.common.exception.AccommodationNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedRequest;
import kr.co.fastcampus.fastcatch.common.exception.RoomNotFoundException;
import kr.co.fastcampus.fastcatch.common.utility.AvailableOrderUtil;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.AccommodationOptionSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.AccommodationSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.RoomImageSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.RoomOptionSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.RoomSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationInfoResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationPageResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSearchPageResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSearchResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSummaryResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.RoomResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.AccommodationRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomOptionRepository;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    private final AccommodationOptionRepository accommodationOptionRepository;

    private final RoomRepository roomRepository;

    private final RoomOptionRepository roomOptionRepository;

    private final OrderRecordRepository orderRecordRepository;

    @Transactional
    public void createAccommodation(AccommodationSaveRequest request) {
        accommodationRepository.save(request.toEntity());
    }

    @Transactional
    public void createAccommodations(List<AccommodationSaveRequest> requests) {
        for (AccommodationSaveRequest request : requests) {
            createAccommodation(request);
        }
    }

    @Transactional
    public void createAccommodationOption(AccommodationOptionSaveRequest request) {
        Long accommodationId = request.accommodationId();
        Accommodation accommodation = findAccommodationById(accommodationId);
        if (accommodationOptionRepository.existsByAccommodationId(accommodationId)) {
            throw new DuplicatedRequest();
        }
        accommodation.registerAccommodationOption(request.toEntity(accommodation));
    }

    @Transactional
    public void createAccommodationOptions(List<AccommodationOptionSaveRequest> requests) {
        for (AccommodationOptionSaveRequest request : requests) {
            createAccommodationOption(request);
        }
    }

    @Transactional
    public void createRoom(Long accommodationId, List<RoomSaveRequest> requests) {
        Accommodation accommodation = findAccommodationById(accommodationId);
        requests.stream()
            .map(roomSaveRequest -> roomSaveRequest.toEntity(accommodation))
            .forEach(accommodation::addRoom);
    }

    @Transactional
    public void createRoomOption(RoomOptionSaveRequest request) {
        Long roomId = request.roomId();
        Room room = findRoomById(roomId);
        if (roomOptionRepository.existsByRoomId(roomId)) {
            throw new DuplicatedRequest();
        }
        room.registerRoomOption(request.toEntity(room));
    }

    @Transactional
    public void createRoomOptions(List<RoomOptionSaveRequest> requests) {
        for (RoomOptionSaveRequest request : requests) {
            createRoomOption(request);
        }
    }

    @Transactional
    public void createRoomImage(Long roomId, List<RoomImageSaveRequest> request) {
        Room room = findRoomById(roomId);
        request.stream()
            .map(roomImageSaveRequest -> roomImageSaveRequest.toEntity(room))
            .forEach(room::addRoomImage);
    }

    public AccommodationPageResponse findAccommodationsWithSoldOutCheck(
        Category category, Region region, LocalDate startDate,
        LocalDate endDate, Integer headCount, Pageable pageable
    ) {
        AvailableOrderUtil.validateDate(startDate, endDate);
        Page<Accommodation> accommodations =
            findFilteredAccommodations(
                category, region, headCount, pageable
            );
        return mapToAccommodationPageResponse(accommodations, startDate, endDate);
    }

    private Page<Accommodation> findFilteredAccommodations(
        Category category, Region region, Integer headCount, Pageable pageable
    ) {
        if (category == Category.ALL && region == Region.ALL) {
            return accommodationRepository.findAccommodationsByHeadCount(
                headCount, pageable
            );
        } else if (category != Category.ALL && region == Region.ALL) {
            return accommodationRepository.findAccommodationsByCategoryAndHeadCount(
                category, headCount, pageable
            );
        } else if (category == Category.ALL) {
            return accommodationRepository.findAccommodationsByRegionAndHeadCount(
                region, headCount, pageable
            );
        } else {
            return accommodationRepository.findAccommodationsByCategoryAndRegionAndHeadCount(
                category, region, headCount, pageable
            );
        }
    }

    private AccommodationPageResponse mapToAccommodationPageResponse(
        Page<Accommodation> accommodations, LocalDate startDate, LocalDate endDate
    ) {
        Page<AccommodationSummaryResponse> accommodationSummaryPageDto =
            accommodations.map(
                accommodation -> mapToAccommodationSummaryResponse(
                    accommodation, startDate, endDate
                )
            );
        return AccommodationPageResponse.from(accommodationSummaryPageDto);
    }

    private AccommodationSummaryResponse mapToAccommodationSummaryResponse(
        Accommodation accommodation, LocalDate startDate, LocalDate endDate
    ) {
        return AccommodationSummaryResponse.from(
            accommodation, isSoldOut(accommodation, startDate, endDate)
        );
    }

    private boolean isSoldOut(
        Accommodation accommodation, LocalDate startDate, LocalDate endDate
    ) {
        return IntStream.range(0, (int) ChronoUnit.DAYS.between(startDate, endDate))
            .anyMatch(offset -> isRoomSoldOutOnDate(accommodation, startDate.plusDays(offset)));
    }

    private boolean isRoomSoldOutOnDate(Accommodation accommodation, LocalDate date) {
        return accommodation.getRooms().size()
            == orderRecordRepository.countByAccommodationIdAndStayDate(accommodation.getId(), date);
    }

    public AccommodationInfoResponse findAccommodationWithRooms(
        Long accommodationId, LocalDate startDate, LocalDate endDate
    ) {
        AvailableOrderUtil.validateDate(startDate, endDate);
        Accommodation accommodation = findAccommodationById(accommodationId);
        List<Room> rooms = accommodation.getRooms();
        List<RoomResponse> roomResponses = convertRoomsToRoomResponses(rooms, startDate, endDate);
        return AccommodationInfoResponse.from(accommodation, roomResponses);
    }

    private List<RoomResponse> convertRoomsToRoomResponses(
        List<Room> rooms, LocalDate startDate, LocalDate endDate
    ) {
        return rooms.stream()
            .map(room -> {
                boolean isSoldOut = IntStream.range(
                    0, (int) ChronoUnit.DAYS.between(startDate, endDate)
                ).anyMatch(offset -> orderRecordRepository.existsByRoomIdAndStayDate(
                    room.getId(), startDate.plusDays(offset))
                );
                return RoomResponse.from(room, isSoldOut);
            })
            .collect(Collectors.toList());
    }

    public Accommodation findAccommodationById(Long id) {
        return accommodationRepository.findById(id)
            .orElseThrow(AccommodationNotFoundException::new);
    }

    public Room findRoomById(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(RoomNotFoundException::new);
    }

    public AccommodationSearchPageResponse findAccommodationByName(
        String query, Pageable pageable
    ) {
        Page<Accommodation> accommodations =
            accommodationRepository.findAllByNameContainingIgnoreCase(query, pageable);
        return AccommodationSearchPageResponse.from(
            accommodations.map(AccommodationSearchResponse::from)
        );
    }
}
