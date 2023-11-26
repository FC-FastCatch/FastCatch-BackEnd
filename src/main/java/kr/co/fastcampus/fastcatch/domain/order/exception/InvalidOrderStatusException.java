package kr.co.fastcampus.fastcatch.domain.order.exception;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException() {
        super("유효한 주문 상태 값이 아닙니다.");
    }
}
