package kr.co.fastcampus.fastcatch.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;


    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "cart",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Cart(
        Long id, Member member, List<CartItem> cartItems
    ) {
        this.id = id;
        this.member = member;
        this.cartItems = cartItems;
    }

    public static Cart createCart(Member member) {
        return Cart.builder()
            .member(member)
            .build();
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

}
