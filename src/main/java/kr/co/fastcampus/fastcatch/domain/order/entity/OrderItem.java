package kr.co.fastcampus.fastcatch.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long orderItemId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer headCount;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @Builder
    public OrderItem(
        Long orderItemId, Order order, Room room, LocalDate startDate,
        LocalDate endDate, Integer headCount, Integer price
    ) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.headCount = headCount;
        this.price = price;
    }

}
