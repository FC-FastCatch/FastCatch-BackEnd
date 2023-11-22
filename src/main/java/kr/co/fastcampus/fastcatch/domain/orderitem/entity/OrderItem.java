package kr.co.fastcampus.fastcatch.domain.orderitem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import kr.co.fastcampus.fastcatch.domain.orders.entity.Orders;
import kr.co.fastcampus.fastcatch.domain.room.entity.Room;
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

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;

    @OneToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @Builder
    public OrderItem(
        Long orderItemId, Orders orders, Room room, LocalDate startDate,
        LocalDate endDate, Integer headCount, Integer price
    ) {
        this.orderItemId = orderItemId;
        this.orders = orders;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.headCount = headCount;
        this.price = price;
    }

}
