package kr.co.fastcampus.fastcatch.common.config;

import kr.co.fastcampus.fastcatch.common.config.jwt.JwtAuthenticationEntryPoint;
import kr.co.fastcampus.fastcatch.common.config.jwt.JwtAuthenticationFilter;
import kr.co.fastcampus.fastcatch.common.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()); // token 방식이므로 csrf 보호 비활성화
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                    new AntPathRequestMatcher("/api/members/signup"),
                    new AntPathRequestMatcher("/api/members/signin"),
                    new AntPathRequestMatcher("/error")).permitAll()
                .requestMatchers(
                    new AntPathRequestMatcher("/api/accommodations/**")).permitAll()
                .requestMatchers(
                    new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .anyRequest().authenticated())
            .httpBasic((httpBasic) -> httpBasic.disable()) //일반적 루트가 아닌 다른 방식 요청 시 거절
            .sessionManagement( //세션 사용하지 않기 때문에 STATELESS로 설정
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //필터 등록: JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenProvider, jwtAuthenticationEntryPoint),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
