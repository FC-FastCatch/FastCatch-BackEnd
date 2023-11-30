package kr.co.fastcampus.fastcatch.domain.order.service;

import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createMember;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrder;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderByCartRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.exception.CartItemNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidDateRangeException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidOrderStatusException;
import kr.co.fastcampus.fastcatch.common.exception.MemberNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.OrderUnauthorizedException;
import kr.co.fastcampus.fastcatch.common.exception.PastDateException;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.service.AccommodationService;
import kr.co.fastcampus.fastcatch.domain.cart.service.CartService;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRecordRepository orderRecordRepository;

    @Mock
    private CartService cartService;

    @Mock
    private MemberService memberService;

    @Mock
    private AccommodationService accommodationService;

    @Nested
    @DisplayName("createOrder()는 ")
    class Context_createOrder {

        @Test
        @DisplayName("시작일이 종료일보다 늦으면 주문을 생성할 수 없다.")
        void invalidDateRange_willFail() {

            //given
            Long memberId = 1L;
            OrderItemRequest orderItemRequest = new OrderItemRequest(1L, LocalDate.of(2023, 12, 03),
                LocalDate.of(2023, 12, 02), 2, 80000);
            OrderRequest orderRequest = createOrderRequest(List.of(orderItemRequest));
            Member member = createMember(memberId);
            Order order = createOrder(member, 1L);
            given(memberService.findMemberById(memberId)).willReturn(member);
            Accommodation accommodation = createAccommodation();
            Room room = createRoom(accommodation);
            given(accommodationService.findRoomById(any(Long.TYPE))).willReturn(room);
            //when
            Throwable exception = assertThrows(InvalidDateRangeException.class, () -> {
                orderService.createOrder(memberId, orderRequest);
            });

            //then
            assertEquals("시작 날짜가 종료 날짜 보다 늦습니다.", exception.getMessage());
            verify(orderRepository, never()).save(any(Order.class));
            verify(orderItemRepository, never()).save(any(OrderItem.class));
            verify(orderRecordRepository, never()).save(any(OrderRecord.class));
        }


        @Test
        @DisplayName("숙박일이 과거 날짜라면 주문을 생성할 수 없다.")
        void pastDate_willFail() {

            //given
            Long memberId = 1L;
            OrderItemRequest orderItemRequest = new OrderItemRequest(1L, LocalDate.of(2023, 11, 23),
                LocalDate.of(2023, 12, 02), 2, 80000);
            OrderRequest orderRequest = createOrderRequest(List.of(orderItemRequest));
            Member member = createMember(memberId);
            Order order = createOrder(member, 1L);
            given(memberService.findMemberById(memberId)).willReturn(member);
            Accommodation accommodation = createAccommodation();
            Room room = createRoom(accommodation);
            given(accommodationService.findRoomById(any(Long.TYPE))).willReturn(room);

            //when, then
            Throwable exception = assertThrows(PastDateException.class, () -> {
                orderService.createOrder(memberId, orderRequest);
            });
            assertEquals("현재 날짜 보다 과거의 날짜는 예약할 수 없습니다.", exception.getMessage());
            verify(orderRepository, never()).save(any(Order.class));
            verify(orderItemRepository, never()).save(any(OrderItem.class));
            verify(orderRecordRepository, never()).save(any(OrderRecord.class));
        }
    }


    @Nested
    @DisplayName("createOrderByCart()는 ")
    class Context_createOrderByCart {

        @Test
        @DisplayName("장바구니 아이템 정보를 조회할 수 없으면 장바구니를 통해 주문을 생성할 수 없다.")
        void cartItemNotFound_willFail() {

            //given
            Long memberId = 1L;
            OrderByCartRequest orderByCartRequest = createOrderByCartRequest(List.of(1L));
            Member member = createMember(memberId);

            given(memberService.findMemberById(any(Long.TYPE))).willReturn(member);
            given(cartService.findCartItemById(1L)).willThrow(new CartItemNotFoundException());

            //when, then
            Throwable exception = assertThrows(CartItemNotFoundException.class, () -> {
                orderService.createOrderByCart(memberId, orderByCartRequest);
            });
            assertEquals("해당 하는 장바구니 아이템이 없습니다.", exception.getMessage());
            verify(orderRepository, never()).save(any(Order.class));
            verify(orderItemRepository, never()).save(any(OrderItem.class));
            verify(orderRecordRepository, never()).save(any(OrderRecord.class));
        }
    }

    @Nested
    @DisplayName("findOrders()는 ")
    class Context_findOrders {

        @Test
        @DisplayName("회원정보를 조회할 수 없으면 주문 목록을 조회할 수 없다.")
        void memberNotFount_willFail() {

            //given
            Long memberId = 1L;
            Pageable pageable = PageRequest.of(0, 3);

            given(memberService.findMemberById(memberId)).willThrow(
                new MemberNotFoundException()
            );

            //when, then
            Throwable exception = assertThrows(MemberNotFoundException.class, () -> {
                orderService.findOrders(memberId, pageable);
            });
            assertEquals("존재하지 않는 회원입니다.", exception.getMessage());
            verify(orderRepository, never()).findOrdersReserved(any(Member.class),
                any(OrderStatus.class), any(LocalDate.class), any(Pageable.class));
        }
    }


    @Nested
    @DisplayName("findOrdersByStatus()는 ")
    class Context_findOrdersByStatus {

        @Test
        @DisplayName("유효하지 않은 주문상태를 조회하면 특정 주문 상태의 주문 목록을 조회할 수 없다.")
        void invalidOrderStatus_willFail() {

            //given
            Pageable pageable = PageRequest.of(1, 3);
            String status = "completed";
            Member member = createMember(1L);
            given(memberService.findMemberById(any(Long.TYPE))).willReturn(any(Member.class));

            //when, then
            Throwable exception = assertThrows(InvalidOrderStatusException.class, () -> {
                orderService.findOrdersByStatus(1L, status, pageable);
            });
            assertEquals("유효하지 않은 주문 상태입니다.", exception.getMessage());
            verify(orderRepository, never()).findOrdersReserved(
                any(Member.class), any(OrderStatus.class), any(LocalDate.class), any(Pageable.class)
            );
        }
    }


    @Nested
    @DisplayName("deleteOrder()는 ")
    class Context_deleteOrder {

        @Test
        @DisplayName("주문을 취소할 수 있다.")
        void _willSuccess() {

            //given
            Long memberId = 1L;
            Long orderId = 1L;
            Member member = createMember(memberId);
            Order order = createOrder(member, orderId);
            Order order2 = createOrder(member, 2L);
            order2.setOrderCanceled();
            given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
            given(orderRepository.save(any(Order.class))).willReturn(order2);
            doNothing().when(orderRecordRepository).deleteByOrder(any(Order.class));

            //when
            orderService.deleteOrder(memberId, orderId);

            //then
            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
            verify(orderRepository, times(1)).save(any(Order.class));
        }

        @Test
        @DisplayName("주문을 등록한 회원정보와 일치하지 않으면 주문을 취소할 수 없다.")
        void unAuthorized_willFail() {

            //given
            Long memberId = 1L;
            Long orderId = 2L;
            Member memberOfOrder = createMember(2L);
            Order order = createOrder(memberOfOrder, orderId);
            given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
            //when, then
            Throwable exception = assertThrows(OrderUnauthorizedException.class, () -> {
                orderService.deleteOrder(memberId, orderId);
            });

            //then
            assertEquals("접근 권한이 없는 주문 정보입니다. ", exception.getMessage());
            verify(orderRepository, never()).save(any(Order.class));
            verify(orderRecordRepository, never()).deleteByOrder(any(Order.class));
        }
    }
}