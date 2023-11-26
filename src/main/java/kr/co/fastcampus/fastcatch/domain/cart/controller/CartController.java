package kr.co.fastcampus.fastcatch.domain.cart.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.cart.dto.request.CartItemRequest;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.service.CartService;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/api/cart")
    public ResponseBody<CartResponse> getCart(
        @RequestBody final Long memberId //나중에 credential로 대체 해야 함
    ) {
        log.info("장바구니의 전체 정보를 가져 왔습니다.");
        return ResponseBody.ok(cartService.findCartItemList(memberId));
    }

    @PostMapping("/api/cart/{cartId}")
    public ResponseBody<CartResponse> addCartItem(
        @PathVariable Long cartId,
        @Valid @RequestBody final CartItemRequest cartItemRequest
    ) {
        log.info("장바구니에 상품을 추가 했습니다.");
        return ResponseBody.ok(cartService.createCartItem(cartId, cartItemRequest));
    }

    @DeleteMapping("/api/cart/{cartId}")
    public ResponseBody<CartResponse> deleteAllCartItem(
        @PathVariable Long cartId
    ) {
        log.info("장바구니에서 전체 상품을 삭제 했습니다.");
        return ResponseBody.ok(cartService.deleteAllCartItem(cartId));
    }

    @DeleteMapping("/api/cart-items/{cartItemId}")
    public ResponseBody<CartResponse> deleteCartItem(
        @PathVariable Long cartItemId
    ) {
        log.info("장바구니에서 선택한 상품을 삭제 했습니다.");
        return ResponseBody.ok(cartService.deleteCartItem(cartItemId));
    }

}
