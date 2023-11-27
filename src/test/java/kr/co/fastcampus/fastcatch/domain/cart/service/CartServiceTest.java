package kr.co.fastcampus.fastcatch.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("장바구니 서비스 테스트")
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @InjectMocks
    private CartService cartService;

    private final Room room = Room.builder().build();
    private final Member member = Member.builder()
        .memberId(1l)
        .email("adsa")
        .password("asdas")
        .name("asd")
        .nickname("ads")
        .phoneNumber("01012334567")
        .birthday(LocalDate.now())
        .build();
    private final Cart defaultCart = Cart.builder()
        .cartId(1l)
        .member(member)
        .cartItems(List.of())
        .build();
    private final CartItem defaultCartItem = CartItem.builder()
        .cartItemId(1l)
        .startDate(LocalDate.now())
        .endDate(LocalDate.now())
        .headCount(3)
        .price(5000)
        .room(room)
        .cart(defaultCart)
        .build();


//    @Test
    void 카트_조회_성공() {
        assertThat(cartService.findCartItemList(1l))
            .usingRecursiveComparison()
            .isEqualTo(List.of(CartResponse.from(defaultCart)));
    }


}