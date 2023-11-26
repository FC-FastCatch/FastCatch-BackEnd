package kr.co.fastcampus.fastcatch.domain.cart.service;

import kr.co.fastcampus.fastcatch.common.utility.AvailableOrderUtil;
import kr.co.fastcampus.fastcatch.domain.cart.dto.request.CartItemRequest;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.cart.exception.NoCartException;
import kr.co.fastcampus.fastcatch.domain.cart.exception.NoCartItemException;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartRepository;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
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

    @Transactional(readOnly = true)
    public CartResponse findCartItemList(Long memberId) {
        return CartResponse.fromDto(getCartByMemberId(memberId));
    }

    private Cart getCartByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId)
            .orElse(
                cartRepository.save(
                    Cart.createCart(
                        //memberId로 멤버 가져오는 서비스 필요함(지금은 repo도 없어서 걍 builder로 썼는데 100퍼 안 됨)
                        Member.builder()
                            .memberId(memberId)
                            .build()
                    )
                )
            );
    }

    /*cartItemRequest는 roomId 받고, cartItem은 room을 받으면 어쩌라는거지*/
    public CartResponse createCartItem(Long cartId, CartItemRequest cartItemRequest) {
        validateCartItemRequest(cartItemRequest);

        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new NoCartException());
        cart.addCartItem(cartItemRequest.toEntity(cartItemRequest));

        return CartResponse.fromDto(cart);
    }

    public CartResponse deleteAllCartItem(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new NoCartException());
        cartItemRepository.deleteAllByCart(cart);

        return CartResponse.fromDto(cart);
    }

    public CartResponse deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new NoCartItemException());
        cartItemRepository.delete(cartItem);

        return CartResponse.fromDto(
            cartRepository.findById(cartItem.getCart().getCartId())
                .orElseThrow(() -> new NoCartException())
        );
    }

    //검증할게 또 생기면 추가
    private void validateCartItemRequest(CartItemRequest cartItemRequest) {
        AvailableOrderUtil.validateDate(cartItemRequest.startDate(), cartItemRequest.endDate());
    }

}


