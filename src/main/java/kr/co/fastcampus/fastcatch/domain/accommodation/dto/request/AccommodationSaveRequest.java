package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;

public record AccommodationSaveRequest(
    String name,
    String address,
    Region region,
    String description,
    Category category
) {

    public Accommodation toEntity() {
        return Accommodation.builder()
            .name(name)
            .address(address)
            .region(region)
            .description(description)
            .category(category)
            .build();
    }
}
