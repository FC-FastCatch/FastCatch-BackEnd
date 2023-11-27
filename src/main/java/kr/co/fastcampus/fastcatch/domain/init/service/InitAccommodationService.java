package kr.co.fastcampus.fastcatch.domain.init.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.init.dto.FirstJsonReadDto;
import kr.co.fastcampus.fastcatch.domain.init.dto.InitAccommodationDto;
import kr.co.fastcampus.fastcatch.domain.init.entity.InitAccommodation;
import kr.co.fastcampus.fastcatch.domain.init.exception.InitAccommodationFailedSaveException;
import kr.co.fastcampus.fastcatch.domain.init.exception.InitAccommodationNotFoundException;
import kr.co.fastcampus.fastcatch.domain.init.repository.InitAccommodationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitAccommodationService {

    private final InitAccommodationRepository initAccommodationRepository;
    private final ResourceLoader resourceLoader;
    private final String filePath = "Accommodation.json";
    private final Gson gson = new Gson();

    @Transactional
    public void loadJsonAndInsertData() {
        List<InitAccommodation> initAccommodationList = loadAccommodationData();
        List<InitAccommodation> saveShopData = initAccommodationRepository.saveAll(
            initAccommodationList);

        if (saveShopData == null) {
            throw new InitAccommodationFailedSaveException();
        }
    }

    private List<InitAccommodation> loadAccommodationData() {

        FirstJsonReadDto fromJson = gson.fromJson(readJsonFile(filePath), FirstJsonReadDto.class);
        List<InitAccommodationDto> dataList = fromJson.getData();
        List<InitAccommodation> initAccommodationList = new ArrayList<>();

        for (InitAccommodationDto data : dataList) {
            initAccommodationList.add(dtoToEntity(data));
        }

        return initAccommodationList;
    }

    private String readJsonFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            InputStreamReader reader = new InputStreamReader(
                resource.getInputStream(), StandardCharsets.UTF_8
            );
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new InitAccommodationNotFoundException();
        }
    }

    private InitAccommodation dtoToEntity(InitAccommodationDto data) {
        return InitAccommodation.builder()
            .name(data.getName())
            .address(data.getAddress())
            .region(data.getRegion())
            .category(data.getCategory())
            .description(data.getDescription())
            .build();
    }

}