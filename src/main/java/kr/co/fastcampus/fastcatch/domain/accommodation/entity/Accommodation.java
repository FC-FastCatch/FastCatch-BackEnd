package kr.co.fastcampus.fastcatch.domain.accommodation.entity;

import java.util.ArrayList;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Formula("(SELECT MAX(r.max_head_count) FROM Room r WHERE r.accommodation_id = id)")
    private int maximumCapacity;

    @Formula("(SELECT MIN(r.price) FROM Room r WHERE r.accommodation_id = id)")
    private int lowestPrice;

    @Column(nullable = true)
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
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
        Region region,
        String description,
        Category category
    ) {
        this.name = name;
        this.address = address;
        this.region = region;
        this.description = description;
        this.category = category;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void registerAccommodationOption(AccommodationOption accommodationOption) {
        this.accommodationOption = accommodationOption;
    }

}
