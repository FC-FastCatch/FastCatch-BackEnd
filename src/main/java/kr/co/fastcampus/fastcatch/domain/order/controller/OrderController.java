package kr.co.fastcampus.fastcatch.domain.order.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders/")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseBody addOrder(@Valid @RequestBody OrderRequest orderRequest) {
        if(!orderRequest.ageConsent()){
            return ResponseBody.fail("14세 이상 이용 동의가 필요합니다.");
        }
        //memberId는 회원 기능 추가 후, 수정 예정
        orderService.createOrder(1L, orderRequest);
        return ResponseBody.ok();
    }

    @DeleteMapping("/{orderId}")
    public ResponseBody modifyOrderStatusToCanceled(@PathVariable Long orderId) {
        //memberId는 회원 기능 추가 후, 수정 예정
        orderService.updateOrderStatusToCanceled(1L, orderId);
        return ResponseBody.ok();
    }
}
