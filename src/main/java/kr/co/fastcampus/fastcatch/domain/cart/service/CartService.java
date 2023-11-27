package kr.co.fastcampus.fastcatch.domain.cart.service;

import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.utility.AvailableOrderUtil;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.service.AccommodationService;
import kr.co.fastcampus.fastcatch.domain.cart.dto.request.CartItemRequest;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.cart.exception.NoCartItemException;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AccommodationService accommodationService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public CartResponse findCartItemList(Long memberId) {
        return CartResponse.from(getCartByMemberId(memberId));
    }

    private Cart getCartByMemberId(Long memberId) {
        Optional<Cart> cart = cartRepository.findByMemberId(memberId);

        if (cart.isPresent()) {
            return cart.get();
        } else {
            Cart newCart = Cart.createCart(memberService.findMemberById(memberId));
            return cartRepository.save(newCart);
        }
    }

    @Transactional
    public CartResponse createCartItem(Long memberId, CartItemRequest cartItemRequest) {
        validateCartItemRequest(cartItemRequest);

        Cart cart = getCartByMemberId(memberId);
        Room room = accommodationService.findRoomById(cartItemRequest.roomId());
        CartItem cartItem = cartItemRequest.toEntity(room);
        cart.addCartItem(cartItem);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        return CartResponse.from(cart);
    }

    @Transactional
    public CartResponse deleteAllCartItem(Long memberId) {
        Cart cart = getCartByMemberId(memberId);
        cart.getCartItems().clear();
        cartItemRepository.deleteAllByCart(cart);

        return CartResponse.from(cart);
    }

    @Transactional
    public CartResponse deleteCartItem(Long memberId, Long cartItemId) {
        Cart cart = getCartByMemberId(memberId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new NoCartItemException());

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return CartResponse.from(
            getCartByMemberId(memberId)
        );
    }

    //검증할게 또 생기면 추가
    private void validateCartItemRequest(CartItemRequest cartItemRequest) {
        AvailableOrderUtil.validateDate(cartItemRequest.startDate(), cartItemRequest.endDate());
    }

}


