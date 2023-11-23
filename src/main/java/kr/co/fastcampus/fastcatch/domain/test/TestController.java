package kr.co.fastcampus.fastcatch.domain.test;

import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/{testId}")
    public ResponseBody<InfoResponse> getTest(@PathVariable Long testId) {
        return ResponseBody.ok(testService.findById(testId));
    }

    @PostMapping
    public ResponseBody<InfoResponse> addTest(@RequestBody SaveRequest request) {
        return ResponseBody.ok(testService.save(request));
    }

}
