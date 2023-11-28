package kr.co.fastcampus.fastcatch.domain.common;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;

public class CartTestUtils {
    private CartTestUtils() {
    }

    public static Member createMember() {
        return Member.builder()
            .memberId(1L)
            .email("adsa")
            .password("asdas")
            .name("asd")
            .nickname("ads")
            .phoneNumber("01012334567")
            .birthday(LocalDate.now())
            .build();
    }

    public static Accommodation createAccommodation() {
        return Accommodation.builder()
            .name("호텔")
            .address("경기도")
            .phoneNumber("01012334567")
            .build();
    }

    public static Room createRoom(Accommodation accommodation) {
        return Room.builder()
            .accommodation(accommodation)
            .name("디럭스 스페셜")
            .price(78950)
            .baseHeadCount(2)
            .maxHeadCount(4)
            .build();
    }

    public static Cart createCart(Member member) {
        return Cart.builder()
            .id(1L)
            .member(member)
            .cartItems(List.of())
            .build();
    }

    public static CartItem createCartItem(Room room, Cart cart) {
        return CartItem.builder()
            .id(1L)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .headCount(3)
            .price(5000)
            .room(room)
            .cart(cart)
            .build();
    }

}
