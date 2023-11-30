package kr.co.fastcampus.fastcatch.domain.order.service;

import static kr.co.fastcampus.fastcatch.common.TestUtil.createAccommodation;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createMember;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrder;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderByCartRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderItem;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderItemRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createRoom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import groovy.util.logging.Log;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.utility.AvailableOrderUtil;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.service.AccommodationService;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import kr.co.fastcampus.fastcatch.domain.cart.service.CartService;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderItemResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderPageResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrdersResponse;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    @DisplayName("createOrder")
    class Context_createOrder {


        @Test
        @DisplayName("주문을 생성할 수 있다.")
        void _willSuccess() {
            //given
            Long memberId = 1L;
            OrderItemRequest orderItemRequest = createOrderItemRequest();
            //List<OrderItemRequest> orderItemRequestList = List.of(orderItemRequest);
            OrderRequest orderRequest = createOrderRequest(List.of(orderItemRequest));

            Member member = createMember(memberId);
            Order order = createOrder(member, 1L);
            given(memberService.findMemberById(memberId)).willReturn(member);
            given(accommodationService.findRoomById(1L)).willReturn(any(Room.class));
            doNothing().when(AvailableOrderUtil.class);
            AvailableOrderUtil.validateDate(any(LocalDate.class), any(LocalDate.class));
            AvailableOrderUtil.validateHeadCount(any(Integer.TYPE), any(Integer.TYPE), any(Integer.TYPE));
            given(orderRepository.save(order)).willReturn(order);

            //when
            OrderResponse orderResponse = orderService.createOrder(memberId, orderRequest);

            //then
            verify(orderRepository, times(1)).save(any(Order.class));
            verify(orderItemRepository, times(orderRequest.orderItems().size())).save(
                any(OrderItem.class));
            verify(orderRecordRepository, times(order.getOrderItems().size())).save(
                any(OrderRecord.class));
        }
    }
/*
    @Nested
    @DisplayName("createOrderByCart()는 ")
    class Context_createOrderByCart {

        @Test
        @DisplayName("장바구니를 통해 주문을 생성할 수 있다.")
        void _willSuccess() {
            //given
            Long memberId = 1L;
            OrderByCartRequest orderByCartRequest = createOrderByCartRequest(List.of(1L));
            Member member = createMember();
            Order order = createOrder(member);

            given(memberService.findMemberById(any(Long.TYPE))).willReturn(any(Member.class));
            given(cartService.findCartItemById(any(Long.TYPE))).willReturn(any(CartItem.class));
            given(OrderItemRequest.fromCartItem(any(CartItem.class))).willReturn(
                any(OrderItemRequest.class));
            doNothing().when(orderService)
                .createOrderItem(any(OrderItemRequest.class), any(Order.class));
            doNothing().when(orderService).checkOrderRecord(any(OrderItemRequest.class));
            doNothing().when(cartService.deleteCartItem(any(Long.class), any(Long.class)));
            given(orderRepository.save(any(Order.class))).willReturn(any(Order.class));
            doNothing().when(orderService).createOrderRecord(any(OrderItem.class));

            //when
            orderService.createOrderByCart(memberId, orderByCartRequest);

            //then
            verify(orderRepository, times(1)).save(any(Order.class));
            verify(orderItemRepository, times(orderByCartRequest.cartItemIds().size())).save(
                any(OrderItem.class));
            verify(orderRecordRepository, times(order.getOrderItems().size())).save(
                any(OrderRecord.class));
        }
    }

    @Nested
    @DisplayName("findOrders()는 ")
    class Context_findOrders {

        @Test
        @DisplayName("주문 목록을 조회할 수 있다.")
        void _willSuccess() {
            //given
            Long memberId = 1L;
            Pageable pageable = PageRequest.of(0, 3);

            Room room = createRoom(createAccommodation());
            Order order1 = createOrder(createMember());
            Order order2 = createOrder(createMember());
            Order order3 = createOrder(createMember());
            List<OrderItemResponse> orderItemResponseList1 = List.of(
                OrderItemResponse.from(createOrderItem(room, order1)));
            List<OrderItemResponse> orderItemResponseList2 = List.of(
                OrderItemResponse.from(createOrderItem(room, order2)));
            List<OrderItemResponse> orderItemResponseList3 = List.of(
                OrderItemResponse.from(createOrderItem(room, order3)));
            OrderResponse orderResponseReserved = OrderResponse.from(order1, orderItemResponseList1,
                "reserved");
            OrderResponse orderResponseUsed = OrderResponse.from(order2, orderItemResponseList2,
                "used");
            OrderResponse orderResponseCanceled = OrderResponse.from(order3, orderItemResponseList3,
                "canceled");

            when(orderService.findOrdersByStatus(any(Long.TYPE), anyString(), any(Pageable.class)))
                .thenReturn(OrderPageResponse.from(new PageImpl<>(List.of(orderResponseReserved)),
                    "reserved"))
                .thenReturn(
                    OrderPageResponse.from(new PageImpl<>(List.of(orderResponseUsed)), "used"))
                .thenReturn(OrderPageResponse.from(new PageImpl<>(List.of(orderResponseCanceled)),
                    "canceled"));

            //when
            OrdersResponse result = orderService.findOrders(memberId, pageable);

            //then
            assertNotNull(result);
            assertThat(result.orders().size()).isEqualTo(3);
            verify(orderService, times(3)).findOrdersByStatus(any(Long.TYPE), anyString(),
                any(Pageable.class));
        }
    }

    @Nested
    @DisplayName("findOrdersByStatus()는 ")
    class Context_findOrdersByStatus {

        @Test
        @DisplayName("특정 주문 상태의 주문목록을 조회할 수 있다.")
        void _willSuccess() {
            //given
            Pageable pageable = PageRequest.of(1, 3);
            String status = "reserved";
            Member member = createMember();
            Order order = createOrder(member);
            OrderItem orderItem = createOrderItem(createRoom(createAccommodation()), order);
            Page<Order> orders = new PageImpl<>(List.of(order));
            OrderResponse orderResponseReserved = OrderResponse.from(order,
                List.of(OrderItemResponse.from(orderItem)), status);

            given(memberService.findMemberById(any(Long.TYPE))).willReturn(any(Member.class));
            given(orderRepository.findOrdersReserved(member, OrderStatus.COMPLETED, LocalDate.now(),
                pageable)).willReturn(orders);

            //when
            OrderPageResponse result = orderService.findOrdersByStatus(member.getMemberId(),
                "reserved", pageable);

            //then
            assertNotNull(result);
            assertThat(result.status()).isEqualTo("reserved");
            assertThat(result.pageNum()).isEqualTo(1);
            assertThat(result.pageSize()).isEqualTo(3);
            assertThat(result.orderResponses()).isEqualTo(List.of(orderResponseReserved));
            verify(orderRepository, times(1)).findOrdersReserved(any(Member.class),
                any(OrderStatus.class), any(LocalDate.class), any(Pageable.class));
        }
    }
*/
    @Nested
    @DisplayName("deleteOrder()는 ")
    class Context_deleteOrder {

        @Test
        @DisplayName("주문을 취소할 수 있다.")
        void _willSuccess() {
            //given
            Long memberId = 1L; Long orderId = 1L;
            Member member = createMember(memberId);
            Order order = createOrder(createMember(memberId), orderId);
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
    }
}