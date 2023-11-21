package kr.co.fastcampus.fastcatch.domain.accommodation.controller;

import java.util.List;

import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.accommodation.dto.AccommodationSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    @GetMapping
    public ResponseBody<List<AccommodationSummaryResponse>> getAccommodationList() {
        return ResponseBody.ok(null);
    }
}