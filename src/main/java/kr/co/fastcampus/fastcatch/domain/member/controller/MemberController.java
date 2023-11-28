package kr.co.fastcampus.fastcatch.domain.member.controller;

import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSignupResponse;
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
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseBody<MemberSignupResponse> addMember(
        @RequestBody MemberSignupRequest request) {
        MemberSignupResponse response = memberService.createMember(request);
        return ResponseBody.ok(response);
    }

}
