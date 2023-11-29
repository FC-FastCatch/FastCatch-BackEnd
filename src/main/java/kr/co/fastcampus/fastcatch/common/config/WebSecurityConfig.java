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
        http.csrf((csrf) -> csrf.disable());
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
            .httpBasic((httpBasic) -> httpBasic.disable())
            .sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenProvider, jwtAuthenticationEntryPoint),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
