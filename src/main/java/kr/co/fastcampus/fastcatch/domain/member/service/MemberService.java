package kr.co.fastcampus.fastcatch.domain.member.service;

import kr.co.fastcampus.fastcatch.common.exception.CartNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedEmailException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedNicknameException;
import kr.co.fastcampus.fastcatch.common.exception.MemberNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.PasswordNotMatchedException;
import kr.co.fastcampus.fastcatch.common.exception.TokenNotMatchedException;
import kr.co.fastcampus.fastcatch.common.security.CustomUserDetailsService;
import kr.co.fastcampus.fastcatch.common.security.jwt.JwtTokenProvider;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignOutRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSigninRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberUpdateRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.ReIssueTokenRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSignOutResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSigninResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.TokenResponse;
import kr.co.fastcampus.fastcatch.domain.member.entity.BlackList;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.repository.BlackListRepository;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartRepository cartRepository;
    private final BlackListRepository blackListRepository;

    public MemberResponse createMember(MemberSignupRequest request) {

        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicatedEmailException();
        }
        if (existsByNickname(request.nickname())) {
            throw new DuplicatedNicknameException();
        }

        Member member = Member.builder()
            .email(request.email())
            .name(request.name())
            .nickname(request.nickname())
            .password(passwordEncoder.encode(request.password()))
            .birthday(request.birthday())
            .phoneNumber(request.phoneNumber())
            .build();

        memberRepository.save(member);
        Long cartId = cartRepository.save(Cart.createCart(member)).getCartId();

        return MemberResponse.from(member, cartId);
    }

    public MemberSigninResponse createSignIn(MemberSigninRequest memberSigninRequest) {
        String email = memberSigninRequest.email();
        String password = memberSigninRequest.password();

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
        String dbPassword = member.getPassword();
        if (!passwordEncoder.matches(password, dbPassword)) {
            throw new PasswordNotMatchedException();
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        Long cartId = findCartIdByMemberId(member.getMemberId());

        return MemberSigninResponse.from(
            accessToken, refreshToken, MemberResponse.from(member, cartId)
        );
    }

    public MemberSignOutResponse createSignOut(Long memberId,
        MemberSignOutRequest memberSignOutRequest) {
        Member member = findMemberById(memberId);
        String accessToken = memberSignOutRequest.accessToken();
        String refreshToken = memberSignOutRequest.refreshToken();
        if (!member.getEmail().equals(jwtTokenProvider.extractEmailFromToken(accessToken))) {
            throw new TokenNotMatchedException();
        }
        BlackList blackList = BlackList.builder().email(member.getEmail())
            .accessToken(accessToken)
            .refreshToken(refreshToken).build();
        blackListRepository.save(blackList);
        return MemberSignOutResponse.builder().blackListId(blackList.getBlackListId())
            .memberId(memberId).email(member.getEmail())
            .accessToken(memberSignOutRequest.accessToken()).refreshToken(
                memberSignOutRequest.refreshToken()).build();
    }

    public TokenResponse recreateAccessToken(
        String headerRefreshToken, ReIssueTokenRequest reIssueTokenRequest) {
        String email = reIssueTokenRequest.email();
        String refreshToken = headerRefreshToken;
        String reAccessToken = "";
        if (StringUtils.hasText(headerRefreshToken) && headerRefreshToken.startsWith("Bearer")) {
            refreshToken = refreshToken.substring(7);
        }
        if (jwtTokenProvider.validateToken(refreshToken)) {
            if (!email.equals(jwtTokenProvider.extractEmailFromToken(refreshToken))) {
                throw new TokenNotMatchedException();
            }
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                jwtTokenProvider.extractEmailFromToken(refreshToken));
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            reAccessToken = jwtTokenProvider.createAccessToken(authentication);
        }
        return TokenResponse.builder().accessToken(reAccessToken).build();
    }

    public MemberResponse findMemberInfo(Long memberId) {

        Long cartId = findCartIdByMemberId(memberId);
        return MemberResponse.from(findMemberById(memberId), cartId);
    }

    public MemberResponse updateMember(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member member = findMemberById(memberId);
        member.updateMember(memberUpdateRequest);

        return MemberResponse.from(member, findCartIdByMemberId(memberId));
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    public Long findCartIdByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId).orElseThrow(CartNotFoundException::new)
            .getCartId();
    }

    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
