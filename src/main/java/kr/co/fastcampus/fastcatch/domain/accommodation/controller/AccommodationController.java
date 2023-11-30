package kr.co.fastcampus.fastcatch.domain.accommodation.controller;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.AccommodationOptionSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.AccommodationSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.RoomImageSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.RoomOptionSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.request.RoomSaveRequest;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationInfoResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationPageResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.response.AccommodationSearchPageResponse;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import kr.co.fastcampus.fastcatch.domain.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/{accommodationId}")
    public ResponseBody<AccommodationInfoResponse> getAccommodationWithRooms(
        @PathVariable Long accommodationId,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate
    ) {
        return ResponseBody.ok(accommodationService.findAccommodationWithRooms(
            accommodationId, startDate, endDate)
        );
    }

    @GetMapping
    public ResponseBody<AccommodationPageResponse> getAccommodations(
        @RequestParam Category category,
        @RequestParam Region region,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate,
        @RequestParam(defaultValue = "2") Integer headCount,
        @PageableDefault(size = 12) Pageable pageable
    ) {
        AccommodationPageResponse accommodations =
            accommodationService.findAccommodationsWithSoldOutCheck(
                category, region, startDate, endDate, headCount, pageable
            );
        return ResponseBody.ok(accommodations);
    }

    @GetMapping("/search-by-name")
    public ResponseBody<AccommodationSearchPageResponse> getAccommodationByName(
        @RequestParam String query,
        @PageableDefault(size = 12) Pageable pageable
    ) {
        return ResponseBody.ok(accommodationService.findAccommodationByName(
            query, pageable)
        );
    }

    @PostMapping
    public ResponseBody<Void> addAccommodation(@RequestBody AccommodationSaveRequest request) {
        accommodationService.createAccommodation(request);
        return ResponseBody.ok();
    }

    @PostMapping("/list")
    public ResponseBody<Void> addAccommodation(
        @RequestBody List<AccommodationSaveRequest> requests
    ) {
        accommodationService.createAccommodations(requests);
        return ResponseBody.ok();
    }

    @PostMapping("/accommodationOption")
    public ResponseBody<Void> addAccommodationOption(
        @RequestBody AccommodationOptionSaveRequest request
    ) {
        accommodationService.createAccommodationOption(request);
        return ResponseBody.ok();
    }

    @PostMapping("/accommodationOptionList")
    public ResponseBody<Void> addAccommodationOption(
        @RequestBody List<AccommodationOptionSaveRequest> requests
    ) {
        accommodationService.createAccommodationOptions(requests);
        return ResponseBody.ok();
    }

    @PostMapping("/{accommodationId}")
    public ResponseBody<Void> addRoom(
        @PathVariable Long accommodationId, @RequestBody List<RoomSaveRequest> requests
    ) {
        accommodationService.createRoom(accommodationId, requests);
        return ResponseBody.ok();
    }

    @PostMapping("/roomOption")
    public ResponseBody<Void> addRoomOption(@RequestBody RoomOptionSaveRequest request) {
        accommodationService.createRoomOption(request);
        return ResponseBody.ok();
    }

    @PostMapping("/roomOptionList")
    public ResponseBody<Void> addRoomOption(@RequestBody List<RoomOptionSaveRequest> requests) {
        accommodationService.createRoomOptions(requests);
        return ResponseBody.ok();
    }

    @PostMapping("/room/{roomId}")
    public ResponseBody<Void> addRoomImage(
        @PathVariable Long roomId, @RequestBody List<RoomImageSaveRequest> request) {
        accommodationService.createRoomImage(roomId, request);
        return ResponseBody.ok();
    }
}
