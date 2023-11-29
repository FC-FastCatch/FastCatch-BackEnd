package kr.co.fastcampus.fastcatch.domain.order.controller;

import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderItemRequest;
import static kr.co.fastcampus.fastcatch.common.TestUtil.createOrderRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @Nested
    @DisplayName("addOrder()는 ")
    class Context_addOrder {
        @Test
        @DisplayName("주문을 생성할 수 있다.")
        void _willSuccess() throws Exception {
            //given
            Long memberId = 1L;
            List<OrderItemRequest> orderItemRequestList = List.of(createOrderItemRequest());
            OrderRequest orderRequest = createOrderRequest(orderItemRequestList);

            doNothing().when(orderService).createOrder(memberId, orderRequest);

            //when, then
            mockMvc.perform(post("/api/orders").content(
                new ObjectMapper().writeValueAsString(orderRequest))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
            verify(orderService, times(1)).createOrder(any(Long.TYPE), any(OrderRequest.class));
        }
    }
    @Nested
    @DisplayName("getOrders()는 ")
    class Context_getOrders {
        @Test
        @DisplayName("주문 목록을 조회할 수 있다.")
        void _willSuccess() throws Exception {
            //given
            Long memberId = 1L;
            List<OrderItemRequest> orderItemRequestList = List.of(createOrderItemRequest());
            OrderRequest orderRequest = createOrderRequest(orderItemRequestList);

            doNothing().when(orderService).createOrder(memberId, orderRequest);

            //when, then
        }
    }

    @Test
    void getOrdersByOrderStatus() {
    }

    @Test
    void modifyOrderStatusToCanceled() {
    }
}