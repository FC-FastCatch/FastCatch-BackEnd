package kr.co.fastcampus.fastcatch.domain.accommodation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccommodationOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(nullable = false)
    private boolean hasSmokingRoom;

    @Column(nullable = false)
    private boolean hasPetRoom;

    @Column(nullable = false)
    private boolean hasParkingLot;

    @Column(nullable = false)
    private boolean hasWifi;

    @Column(nullable = false)
    private boolean hasSwimmingPool;

    @Column(nullable = false)
    private boolean hasGym;

    @Column(nullable = false)
    private boolean hasBreakfast;

    @Column(nullable = false)
    private boolean hasRestaurant;

    @Column(nullable = false)
    private boolean hasCookingRoom;

    @Builder
    private AccommodationOption(
        Accommodation accommodation,
        boolean hasSmokingRoom,
        boolean hasPetRoom,
        boolean hasParkingLot,
        boolean hasWifi,
        boolean hasSwimmingPool,
        boolean hasGym,
        boolean hasBreakfast,
        boolean hasRestaurant,
        boolean hasCookingRoom

    ) {
        this.accommodation = accommodation;
        this.hasSmokingRoom = hasSmokingRoom;
        this.hasPetRoom = hasPetRoom;
        this.hasParkingLot = hasParkingLot;
        this.hasWifi = hasWifi;
        this.hasSwimmingPool = hasSwimmingPool;
        this.hasGym = hasGym;
        this.hasBreakfast = hasBreakfast;
        this.hasRestaurant = hasRestaurant;
        this.hasCookingRoom = hasCookingRoom;
    }

}
