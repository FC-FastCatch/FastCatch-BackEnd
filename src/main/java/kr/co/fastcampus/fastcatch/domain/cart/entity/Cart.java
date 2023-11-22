package kr.co.fastcampus.fastcatch.domain.cart.entity;

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
import kr.co.fastcampus.fastcatch.domain.cart_item.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "cart",
        cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private List<CartItem> cartItems = new ArrayList<>();

}
