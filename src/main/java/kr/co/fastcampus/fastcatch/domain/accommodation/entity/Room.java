package kr.co.fastcampus.fastcatch.domain.accommodation.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int baseHeadCount;

    @Column(nullable = false)
    private int maxHeadCount;

    @Column(nullable = false, length = 1000)
    private String description;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime checkInTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime checkOutTime;

    @OneToOne
    private RoomOption roomOption;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "room",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<RoomImage> roomImages = new ArrayList<>();

    @Builder
    public Room(
        Accommodation accommodation,
        String name,
        int price,
        int baseHeadCount,
        int maxHeadCount,
        String description,
        LocalTime checkInTime,
        LocalTime checkOutTime,
        RoomOption roomOption
    ) {
        this.accommodation = accommodation;
        this.name = name;
        this.price = price;
        this.baseHeadCount = baseHeadCount;
        this.maxHeadCount = maxHeadCount;
        this.description = description;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.roomOption = roomOption;
    }

    public void registerRoomOption(RoomOption roomOption) {
        this.roomOption = roomOption;
    }

    public void addRoomImage(RoomImage roomImage) {
        this.roomImages.add(roomImage);
    }
}
