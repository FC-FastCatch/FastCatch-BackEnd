package kr.co.fastcampus.fastcatch.domain.order.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate stayDate;

    @Builder
    private OrderRecord(
        Accommodation accommodation,
        Room room,
        LocalDate stayDate
    ) {
        this.accommodation = accommodation;
        this.room = room;
        this.stayDate = stayDate;
    }
}
