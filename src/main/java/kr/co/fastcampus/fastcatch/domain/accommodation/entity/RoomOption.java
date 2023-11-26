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
public class RoomOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private boolean canSmoking;

    @Column(nullable = false)
    private boolean petAccompanying;

    @Column(nullable = false)
    private boolean cityView;

    @Column(nullable = false)
    private boolean oceanView;

    @Column(nullable = false)
    private boolean hasNetflix;

    @Column(nullable = false)
    private boolean canCooking;

    @Builder
    public RoomOption(
        Room room,
        boolean canSmoking,
        boolean petAccompanying,
        boolean cityView,
        boolean oceanView,
        boolean hasNetflix,
        boolean canCooking
    ) {
        this.room = room;
        this.canSmoking = canSmoking;
        this.petAccompanying = petAccompanying;
        this.cityView = cityView;
        this.oceanView = oceanView;
        this.hasNetflix = hasNetflix;
        this.canCooking = canCooking;
    }
}
