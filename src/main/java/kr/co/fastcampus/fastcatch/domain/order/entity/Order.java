package kr.co.fastcampus.fastcatch.domain.order.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.order_item.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    @NonNull
    private String reservationPersonName;

    @NonNull
    private String phoneNumber;

    @NonNull
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "order",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(
        Long id, String reservationPersonName, String phoneNumber,
        Integer totalPrice, OrderStatus orderStatus) {
        this.id = id;
        this.reservationPersonName = reservationPersonName;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
}
