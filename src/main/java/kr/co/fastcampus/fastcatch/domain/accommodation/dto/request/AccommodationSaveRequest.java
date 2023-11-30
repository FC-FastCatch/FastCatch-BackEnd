package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;

public record AccommodationSaveRequest(
    @NotBlank(message = "이름을 필수로 입력하셔야 합니다.")
    String name,
    @NotBlank(message = "주소를 필수로 입력하셔야 합니다.")
    String address,
    @NotBlank(message = "전화 번호를 필수로 입력하셔야 합니다.")
    String phoneNumber,
    @NotBlank(message = "경도를 필수로 입력하셔야 합니다.")
    String longitude,
    @NotBlank(message = "위도를 필수로 입력하셔야 합니다.")
    String latitude,
    @NotNull(message = "지역을 필수로 입력하셔야 합니다.")
    Region region,
    @NotBlank(message = "상세 소개를 필수로 입력하셔야 합니다.")
    String description,
    @NotNull(message = "카테고리 필수로 입력하셔야 합니다.")
    Category category,
    String image
) {

    public Accommodation toEntity() {
        return Accommodation.builder()
            .name(name)
            .address(address)
            .phoneNumber(phoneNumber)
            .longitude(longitude)
            .latitude(latitude)
            .region(region)
            .description(description)
            .category(category)
            .image(image)
            .build();
    }
}
