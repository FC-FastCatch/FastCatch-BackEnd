package kr.co.fastcampus.fastcatch.domain.accommodation.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Accommodation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String longitude;

    @Column(nullable = false, length = 20)
    private String latitude;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Formula("(SELECT MAX(r.max_head_count) FROM room r WHERE r.accommodation_id = id)")
    private Integer maximumCapacity;

    @Formula("(SELECT MIN(r.price) FROM room r WHERE r.accommodation_id = id)")
    private Integer lowestPrice;

    @Column(nullable = true)
    private String image;

    @OneToOne(
        fetch = FetchType.LAZY, mappedBy = "accommodation",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private AccommodationOption accommodationOption;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "accommodation",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<Room> rooms = new ArrayList<>();

    @Builder
    private Accommodation(
        String name,
        String address,
        String phoneNumber,
        String longitude,
        String latitude,
        Region region,
        String description,
        Category category,
        String image
    ) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.region = region;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void registerAccommodationOption(AccommodationOption accommodationOption) {
        this.accommodationOption = accommodationOption;
    }

}
