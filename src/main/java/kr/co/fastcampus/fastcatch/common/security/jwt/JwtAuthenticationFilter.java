package kr.co.fastcampus.fastcatch.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.fastcampus.fastcatch.domain.member.service.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final BlackListService blackListService;

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        String token = resolveToken((HttpServletRequest) request);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();
        if (requestUri.equals("/api/members/signup") || requestUri.equals("/api/members/signin")
            || requestUri.equals("/error") || requestUri.startsWith("/api/accommodations")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            if (token != null && jwtTokenProvider.validateToken(token) && !blackListService.existsByAccessTokenInBlackList(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            jwtAuthenticationEntryPoint.commence((HttpServletRequest) request,
                (HttpServletResponse) response, e);
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}