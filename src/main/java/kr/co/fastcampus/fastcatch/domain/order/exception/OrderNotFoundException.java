package kr.co.fastcampus.fastcatch.domain.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("주문 정보를 찾을 수 없습니다.");
    }
}
