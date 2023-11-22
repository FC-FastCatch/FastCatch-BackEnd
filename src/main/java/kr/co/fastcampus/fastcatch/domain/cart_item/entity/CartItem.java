package kr.co.fastcampus.fastcatch.domain.cart_item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.room.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    private Integer headCount;

    @NonNull
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public CartItem(
        Long id, Cart cart, Room room, LocalDate startDate,
        LocalDate endDate, Integer headCount, Integer price
    ) {
        this.id = id;
        this.cart = cart;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.headCount = headCount;
        this.price = price;
    }

}
