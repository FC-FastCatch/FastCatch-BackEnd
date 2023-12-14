package kr.co.fastcampus.fastcatch.domain.member.service;

import static kr.co.fastcampus.fastcatch.common.response.ErrorCode.UNAUTHORIZED_TOKEN;

import io.jsonwebtoken.JwtException;
import kr.co.fastcampus.fastcatch.domain.member.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public boolean existsByAccessTokenInBlackList(String accessToken) {
        if(blackListRepository.existsByAccessToken(accessToken)) {
            throw new JwtException(UNAUTHORIZED_TOKEN.getErrorMsg());
        }
        return false;
    }
}
