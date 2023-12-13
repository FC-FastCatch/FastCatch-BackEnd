package kr.co.fastcampus.fastcatch.domain.member.service;

import io.jsonwebtoken.JwtException;
import kr.co.fastcampus.fastcatch.common.exception.CartNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedEmailException;
import kr.co.fastcampus.fastcatch.common.exception.DuplicatedNicknameException;
import kr.co.fastcampus.fastcatch.common.exception.ExpiredTokenException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidTokenException;
import kr.co.fastcampus.fastcatch.common.exception.MemberNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.PasswordNotMatchedException;
import kr.co.fastcampus.fastcatch.common.exception.TokenNotMatchedException;
import kr.co.fastcampus.fastcatch.common.security.CustomUserDetailsService;
import kr.co.fastcampus.fastcatch.common.security.jwt.JwtTokenProvider;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSigninRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberSignupRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.MemberUpdateRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.request.ReIssueTokenRequest;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.MemberSigninResponse;
import kr.co.fastcampus.fastcatch.domain.member.dto.response.TokenResponse;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
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

    public TokenResponse recreateAccessToken(ReIssueTokenRequest reIssueTokenRequest) {
        String email = reIssueTokenRequest.email();
        String refreshToken = reIssueTokenRequest.refreshToken();
        String reAccessToken = "";
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer")) {
            refreshToken = refreshToken.substring(7);
        }
        if (!email.equals(jwtTokenProvider.extractEmailFromToken(refreshToken))) {
            throw new TokenNotMatchedException();
        }
        try {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                    jwtTokenProvider.extractEmailFromToken(refreshToken));
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                reAccessToken = jwtTokenProvider.createAccessToken(authentication);
            }
        } catch (ExpiredTokenException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
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
