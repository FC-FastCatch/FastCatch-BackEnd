package kr.co.fastcampus.fastcatch.domain.cart.service;

import static kr.co.fastcampus.fastcatch.domain.common.CartTestUtils.createAccommodation;
import static kr.co.fastcampus.fastcatch.domain.common.CartTestUtils.createCart;
import static kr.co.fastcampus.fastcatch.domain.common.CartTestUtils.createCartItem;
import static kr.co.fastcampus.fastcatch.domain.common.CartTestUtils.createMember;
import static kr.co.fastcampus.fastcatch.domain.common.CartTestUtils.createRoom;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
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

    //    @Test
    @DisplayName("카트 조회 성공")
    void findCartItemList_success() {
        Accommodation defaultAccommodation = createAccommodation();
        Room defaultRoom = createRoom(defaultAccommodation);
        Member defaultMember = createMember();
        Cart defaultCart = createCart(defaultMember);
        CartItem defaultCartItem = createCartItem(defaultRoom, defaultCart);

        assertThat(cartService.findCartItemList(1L))
            .usingRecursiveComparison()
            .isEqualTo(List.of(CartResponse.from(defaultCart)));
    }


}