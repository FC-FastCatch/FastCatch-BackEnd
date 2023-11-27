package kr.co.fastcampus.fastcatch.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.baseentity.BaseEntity;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_room")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long orderId;

    @Column(length = 30, nullable = false)
    private String reservationPersonName;

    @Column(nullable = false)
    private String reservationPhoneNumber;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "order",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(
        Long orderId, Member member, String reservationPersonName, String reservationPhoneNumber,
        Integer totalPrice, OrderStatus orderStatus
    ) {
        this.orderId = orderId;
        this.member = member;
        this.reservationPersonName = reservationPersonName;
        this.reservationPhoneNumber = reservationPhoneNumber;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public void setOrderCanceled() {
        this.orderStatus = OrderStatus.CANCELED;
    }

    public LocalDate getOrderDate() {
        return this.getCreatedDate().toLocalDate();
    }
}
