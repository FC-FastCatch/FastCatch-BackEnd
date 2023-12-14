package kr.co.fastcampus.fastcatch.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    ACCOMMODATION_NOT_FOUND("존재하지 않는 숙박 장소입니다."),
    ROOM_NOT_FOUND("존재하지 않는 객실입니다."),
    DUPLICATED_REQUEST("중복된 요청입니다."),
    DUPLICATED_EMAIL("이미 등록된 이메일입니다."),
    DUPLICATED_NICKNAME("이미 사용중인 닉네임입니다."),
    CART_NOT_FOUND("해당 하는 장바구니가 없습니다."),
    CART_ITEM_NOT_FOUND("해당 하는 장바구니 아이템이 없습니다."),
    PAST_DATE("현재 날짜 보다 과거의 날짜는 예약할 수 없습니다."),
    INVALID_DATE_RANGE("시작 날짜가 종료 날짜 보다 늦습니다."),
    INVALID_HEAD_COUNT_RANGE("해당 인원 수로 예약할 수 없습니다."),
    ORDER_NOT_FOUND("존재하지 않는 주문 정보입니다."),
    ORDER_UNAUTHORIZED("접근 권한이 없는 주문 정보입니다. "),
    INVALID_ORDER_STATUS("유효하지 않은 주문 상태입니다."),
    ALREADY_RESERVED_ROOM("이미 예약된 객실입니다."),
    ALREADY_ORDER_CANCELED("이미 본인이 취소한 주문입니다."),
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),
    PASSWORD_NOT_MATCHED("비밀번호가 틀렸습니다."),
    TOKEN_NOT_MATCHED("회원정보가 일치하지 않는 토큰입니다."),
    UNAUTHORIZED_TOKEN("로그인이 필요합니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
