package kr.co.fastcampus.fastcatch.domain.cart.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartItemErrorCode {

    NO_CART_ITEM("해당 하는 카트 아이템이 없습니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
