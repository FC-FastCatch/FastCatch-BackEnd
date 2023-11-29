package kr.co.fastcampus.fastcatch.domain.cart.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.config.jwt.CustomUserDetails;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.cart.dto.request.CartItemRequest;
import kr.co.fastcampus.fastcatch.domain.cart.dto.response.CartResponse;
import kr.co.fastcampus.fastcatch.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/api/carts")
    public ResponseBody<CartResponse> getCart(
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        log.info("장바구니의 전체 정보를 가져 왔습니다.");
        return ResponseBody.ok(
            cartService.findCartItemList(customUserDetails.getMemberId()));
    }

    @PostMapping("/api/carts/{cartId}")
    public ResponseBody<CartResponse> addCartItem(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @RequestBody final CartItemRequest cartItemRequest
    ) {
        log.info("장바구니에 상품을 추가 했습니다.");
        return ResponseBody.ok(
            cartService.createCartItem(customUserDetails.getMemberId(), cartItemRequest));
    }

    @DeleteMapping("/api/carts/{cartId}")
    public ResponseBody<CartResponse> deleteAllCartItem(
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        log.info("장바구니에서 전체 상품을 삭제 했습니다.");
        return ResponseBody.ok(
            cartService.deleteAllCartItem(customUserDetails.getMemberId()));
    }

    @DeleteMapping("/api/cart-items/{cartItemId}")
    public ResponseBody<CartResponse> deleteCartItem(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long cartItemId
    ) {
        log.info("장바구니에서 선택한 상품을 삭제 했습니다.");
        return ResponseBody.ok(
            cartService.deleteCartItem(customUserDetails.getMemberId(), cartItemId));
    }

}
