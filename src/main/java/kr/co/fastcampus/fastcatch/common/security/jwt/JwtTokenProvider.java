package kr.co.fastcampus.fastcatch.common.security.jwt;

import static kr.co.fastcampus.fastcatch.common.response.ErrorCode.EXPIRED_TOKEN;
import static kr.co.fastcampus.fastcatch.common.response.ErrorCode.ILLEGAL_ARGUMENT_TOKEN;
import static kr.co.fastcampus.fastcatch.common.response.ErrorCode.MALFORMED_TOKEN;
import static kr.co.fastcampus.fastcatch.common.response.ErrorCode.SIGNATURE_TOKEN;
import static kr.co.fastcampus.fastcatch.common.response.ErrorCode.UNSUPPORTED_TOKEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import kr.co.fastcampus.fastcatch.common.security.CustomUserDetails;
import kr.co.fastcampus.fastcatch.common.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.accessTokenValidTime}")
    private long accessTokenValidTime;
    @Value("${jwt.refreshTokenValidTime}")
    private long refreshTokenValidTime;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public Authentication getAuthentication(String token) {
        String email = extractEmailFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    public String createAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date now = new Date();

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException(EXPIRED_TOKEN.getErrorMsg());
        } catch (UnsupportedJwtException e) {
            throw new JwtException(UNSUPPORTED_TOKEN.getErrorMsg());
        } catch (MalformedJwtException e) {
            throw new JwtException(MALFORMED_TOKEN.getErrorMsg());
        } catch (SignatureException e) {
            throw new JwtException(SIGNATURE_TOKEN.getErrorMsg());
        } catch (IllegalArgumentException e) {
            throw new JwtException(ILLEGAL_ARGUMENT_TOKEN.getErrorMsg());
        }
    }
}
