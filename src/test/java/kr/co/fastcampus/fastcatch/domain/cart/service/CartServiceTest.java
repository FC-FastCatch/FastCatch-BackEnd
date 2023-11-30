package kr.co.fastcampus.fastcatch.domain.cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import kr.co.fastcampus.fastcatch.common.exception.CartItemNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.MemberNotFoundException;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    CartRepository cartRepository;
    @Mock
    CartItemRepository cartItemRepository;
    @Mock
    MemberService memberService;

    @InjectMocks
    CartService cartService;

    @Test
    @DisplayName("존재 하지 않는 회원으로 장바구니 생성 시 예외 처리")
    void createNewCart_noMember_shouldThrowException() {
        when(cartService.createNewCart(anyLong()))
            .thenThrow(new MemberNotFoundException());

    }

    @Test
    @DisplayName("존재 하지 않는 장바구니 아이템 아이디로 조회 시 예외 처리")
    void findCartItemById_fail_noCartItem() {
        assertThatThrownBy(() ->
            cartService.findCartItemById(2L))
            .isInstanceOf(CartItemNotFoundException.class);

    }

    @Test
    @DisplayName("존재 하지 않는 장바구니 아이템 아이디 삭제 시 예외 처리")
    void deleteCartItem() {
        assertThatThrownBy(() ->
            cartService.findCartItemById(2L))
            .isInstanceOf(CartItemNotFoundException.class);
    }
}