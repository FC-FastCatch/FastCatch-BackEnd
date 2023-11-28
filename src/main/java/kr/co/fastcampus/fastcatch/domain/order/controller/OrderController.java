package kr.co.fastcampus.fastcatch.domain.order.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderPageResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrdersResponse;
import kr.co.fastcampus.fastcatch.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseBody addOrder(@Valid @RequestBody OrderRequest orderRequest) {
        if (!orderRequest.ageConsent()) {
            return ResponseBody.fail("14세 이상 이용 동의가 필요합니다.");
        }
        orderService.createOrder(1L, orderRequest);
        return ResponseBody.ok();
    }

    @PostMapping("/carts")
    public ResponseBody addOrderByCart(@Valid @RequestBody OrderByCartRequest orderByCartRequest) {
        if (!orderByCartRequest.ageConsent()) {
            return ResponseBody.fail("14세 이상 이용 동의가 필요합니다.");
        }
        orderService.createOrderByCart(1L, orderByCartRequest);
        return ResponseBody.ok();
    }

    @GetMapping
    public ResponseBody<OrdersResponse> getOrders(
        @PageableDefault(page = 0, size = 3, sort = "createdDate",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseBody.ok(orderService.findOrders(1L, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseBody<OrderPageResponse> getOrdersByOrderStatus(
        @PathVariable String status,
        @PageableDefault(size = 3, sort = "createdDate", direction = Sort.Direction.DESC)
        Pageable pageable) {
        return ResponseBody.ok(orderService.findOrdersByStatus(1L, status, pageable));
    }

    @DeleteMapping("/{orderId}")
    public ResponseBody modifyOrderStatusToCanceled(@PathVariable Long orderId) {
        orderService.deleteOrder(1L, orderId);
        return ResponseBody.ok();
    }
}
