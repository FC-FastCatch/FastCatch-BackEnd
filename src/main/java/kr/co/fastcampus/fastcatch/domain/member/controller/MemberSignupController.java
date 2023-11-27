package kr.co.fastcampus.fastcatch.domain.member.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.member.dto.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.MemberSignupResponse;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberSignupController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseBody<MemberSignupResponse> addMember(@Valid @RequestBody MemberSignupRequest request) {
        MemberSignupResponse response = memberService.createMember(request);
        return ResponseBody.ok(response);
    }

}
