package kr.co.fastcampus.fastcatch.domain.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.exception.CartItemNotFoundException;
import kr.co.fastcampus.fastcatch.common.utility.AvailableOrderUtil;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.service.AccommodationService;
import kr.co.fastcampus.fastcatch.domain.cart.dto.request.CartItemRequest;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartItemListResponse;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartItemResponse;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
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

    @Transactional
    public Cart createNewCart(Long memberId) {
        return cartRepository.save(
            Cart.createCart(memberService.findMemberById(memberId))
        );
    }

    @Transactional
    public CartResponse findCartItemList(Long memberId) {
        return CartResponse.from(findCartByMemberId(memberId));
    }

    private Cart findCartByMemberId(Long memberId) {
        Optional<Cart> cart = cartRepository.findByMemberId(memberId);

        if (cart.isPresent()) {
            return cart.get();
        } else {
            return createNewCart(memberId);
        }
    }

    @Transactional(readOnly = true)
    public CartItem findCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
            .orElseThrow(CartItemNotFoundException::new);
    }

    @Transactional
    public CartResponse createCartItem(Long memberId, CartItemRequest cartItemRequest) {
        validateCartItemRequest(cartItemRequest);

        Cart cart = findCartByMemberId(memberId);
        Room room = accommodationService.findRoomById(cartItemRequest.roomId());
        CartItem cartItem = cartItemRequest.toEntity(room);
        cart.addCartItem(cartItem);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        List<CartItemListResponse>
            cartItemListResponseList = createCartItemListResponses(cart);

        return CartResponse.setCartItemResponseList(cartItemListResponseList);
    }

    private List<CartItemListResponse> createCartItemListResponses(Cart cart) {
        List<CartItemListResponse> cartItemListResponseList = new ArrayList<>();
        List<CartItem> cartItemList = cart.getCartItems();

        for (CartItem item : cartItemList) {
            if (cartItemListResponseList.stream()
                .anyMatch(c -> c.accommodationId() == item.getRoom().getAccommodation().getId())
            ) {
                CartItemListResponse oldCartItemListResponse = cartItemListResponseList.stream()
                    .filter(c -> c.accommodationId() == item.getRoom().getAccommodation().getId())
                    .findFirst()
                    .get();
                cartItemListResponseList.remove(oldCartItemListResponse);

                List<CartItemResponse> cartItemResponseList = new ArrayList<>();
                for (CartItemResponse roomItem : oldCartItemListResponse.rooms()) {
                    cartItemResponseList.add(roomItem);
                }
                cartItemResponseList.add(CartItemResponse.from(item));

                cartItemListResponseList.add(
                    CartItemListResponse.setRoomsList(item, cartItemResponseList)
                );
            } else {
                CartItemListResponse newCartItemListResponse = CartItemListResponse.from(item);
                cartItemListResponseList.add(newCartItemListResponse);
            }
        }
        return cartItemListResponseList;
    }

    @Transactional
    public CartResponse deleteAllCartItem(Long memberId) {
        Cart cart = findCartByMemberId(memberId);
        cart.getCartItems().clear();
        cartItemRepository.deleteAllByCart(cart);

        return CartResponse.from(cart);
    }

    @Transactional
    public CartResponse deleteCartItem(Long memberId, Long cartItemId) {
        Cart cart = findCartByMemberId(memberId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(CartItemNotFoundException::new);

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return CartResponse.from(
            findCartByMemberId(memberId)
        );
    }

    private void validateCartItemRequest(CartItemRequest cartItemRequest) {
        AvailableOrderUtil.validateDate(cartItemRequest.startDate(), cartItemRequest.endDate());

        Room room = accommodationService.findRoomById(cartItemRequest.roomId());
        AvailableOrderUtil.validateHeadCount(cartItemRequest.headCount(), room.getBaseHeadCount(),
            room.getMaxHeadCount());
    }


}


