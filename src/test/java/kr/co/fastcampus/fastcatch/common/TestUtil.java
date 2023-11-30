package kr.co.fastcampus.fastcatch.common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;

public class TestUtil {

    private TestUtil() {
    }

    public static Accommodation createAccommodation() {
        return Accommodation.builder()
            .name("testHotel")
            .category(Category.HOTELRESORT)
            .address("testAddress")
            .region(Region.SEOUL)
            .latitude("37.5645211757")
            .longitude("126.9800598249")
            .phoneNumber("0266665555")
            .description("testDescription")
            .image("a.jpg")
            .build();
    }

    public static AccommodationOption createAccommodationOption(Accommodation accommodation) {
        return AccommodationOption.builder()
            .accommodation(accommodation)
            .hasBreakfast(false)
            .hasCookingRoom(false)
            .hasGym(false)
            .hasParkingLot(false)
            .hasPetRoom(false)
            .hasSwimmingPool(false)
            .hasWifi(true)
            .hasRestaurant(false)
            .hasSmokingRoom(false)
            .build();
    }

    public static Room createRoom(Accommodation accommodation) {
        return Room.builder()
            .accommodation(accommodation)
            .name("testRoom")
            .description("testDescription")
            .checkInTime(LocalTime.of(14, 0))
            .checkOutTime(LocalTime.of(12, 0))
            .baseHeadCount(2)
            .maxHeadCount(4)
            .price(45000)
            .build();
    }

    public static RoomOption createRoomOption(Room room) {
        return RoomOption.builder()
            .room(room)
            .canCooking(false)
            .cityView(false)
            .oceanView(false)
            .petAccompanying(false)
            .canSmoking(false)
            .hasNetflix(true)
            .build();
    }

    public static Member createMember(Long id) {
        return Member.builder().memberId(id).email("test@naver.com")
            .password("test1234").name("test").nickname("test").phoneNumber("01012341234")
            .birthday(LocalDate.of(2000, 1, 1)).build();
    }

    public static Order createOrder(Member member, Long id) {
        return Order.builder().orderId(id).member(member)
            .reservationPersonName("test").reservationPhoneNumber("01012341234")
            .totalPrice(80000).orderStatus(OrderStatus.COMPLETED).build();
    }

    public static OrderItem createOrderItem(Room room, Order order) {
        return OrderItem.builder().orderItemId(1L).order(order).room(room)
            .startDate(LocalDate.of(2023, 12, 01))
            .endDate(LocalDate.of(2023, 12, 02)).headCount(2).price(80000).build();
    }

    public static OrderRequest createOrderRequest(List<OrderItemRequest> orderItemRequestList) {
        return new OrderRequest(
            true, "test", "01012341234", 80000, orderItemRequestList);
    }

    public static OrderItemRequest createOrderItemRequest() {
        return new OrderItemRequest(1L, LocalDate.of(2023, 12, 01),
            LocalDate.of(2023, 12, 02), 2, 80000);
    }

    public static OrderByCartRequest createOrderByCartRequest(List<Long> cartIdList) {
        return new OrderByCartRequest(true, "test", "01012341234",
            80000, cartIdList);
    }

    public static Member createMember() {
        return Member.builder()
            .memberId(1L)
            .email("ad@asd.com")
            .password("password")
            .name("das")
            .nickname("fd")
            .birthday(LocalDate.of(1023, 12, 12))
            .phoneNumber("01000001111")
            .build();
    }

    public static Cart createCart(Member member) {
        return Cart.builder()
            .cartId(1L)
            .member(member)
            .cartItems(new ArrayList<>())
            .build();
    }

    public static CartItem createCartItem(Room room, Cart cart) {
        return CartItem.builder()
            .cartItemId(1L)
            .startDate(LocalDate.of(2023, 12, 12))
            .endDate(LocalDate.of(2023, 12, 13))
            .headCount(room.getBaseHeadCount())
            .price(room.getPrice())
            .room(room)
            .cart(cart)
            .build();
    }
}
