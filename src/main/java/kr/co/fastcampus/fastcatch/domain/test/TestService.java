package kr.co.fastcampus.fastcatch.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestRepository testRepository;

    public InfoResponse findById(Long testId) {
        TestEntity testEntity = testRepository.findById(testId).orElseThrow();
        return InfoResponse.from(testEntity);
    }

    @Transactional
    public InfoResponse save(SaveRequest request) {
        TestEntity testEntity = request.toEntity();
        TestEntity result = testRepository.save(testEntity);
        return InfoResponse.from(result);
    }
}
