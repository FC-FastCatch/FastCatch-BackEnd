package kr.co.fastcampus.fastcatch.domain.order.exception;

public class OrderUnauthorizedException extends RuntimeException {
    public OrderUnauthorizedException() {
        super("해당 주문 정보에 대한 접근 권한이 없습니다.");
    }

}
