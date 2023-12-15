package kr.co.fastcampus.fastcatch.domain.member.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.common.security.CustomUserDetails;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignOutRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSigninRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberUpdateRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.ReIssueTokenRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSignOutResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSigninResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.TokenResponse;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseBody<MemberResponse> addMember(
        @Valid @RequestBody MemberSignupRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseBody.ok(response);
    }

    @PostMapping("/signin")
    public ResponseBody<MemberSigninResponse> signIn(
        @Valid @RequestBody MemberSigninRequest request) {
        return ResponseBody.ok(memberService.createSignIn(request));
    }

    @PostMapping("/signout")
    public ResponseBody<MemberSignOutResponse> signOut(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @RequestBody MemberSignOutRequest request) {
        return ResponseBody.ok(
            memberService.createSignOut(customUserDetails.getMemberId(), request));
    }

    @PostMapping("/re-token")
    public ResponseBody<TokenResponse> addReAccessToken(
        @RequestHeader(value = "Authorization") String headerRefreshToken,
        @Valid @RequestBody ReIssueTokenRequest request) {
        return ResponseBody.ok(memberService.recreateAccessToken(headerRefreshToken, request));
    }

    @GetMapping("/nickname")
    public ResponseBody<Boolean> getNickname(
        @RequestParam String nickname
    ) {
        boolean nicknameExists = memberService.existsByNickname(nickname);
        return ResponseBody.ok(nicknameExists);
    }

    @GetMapping
    public ResponseBody<MemberResponse> getMemberInfo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseBody.ok(memberService.findMemberInfo(customUserDetails.getMemberId()));
    }

    @PutMapping
    public ResponseBody<MemberResponse> modifyMemberInfo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @RequestBody MemberUpdateRequest memberUpdateRequest
    ) {
        return ResponseBody.ok(
            memberService.updateMember(customUserDetails.getMemberId(), memberUpdateRequest));
    }
}
