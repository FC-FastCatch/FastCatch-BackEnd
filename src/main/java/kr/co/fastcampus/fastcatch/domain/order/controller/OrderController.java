package kr.co.fastcampus.fastcatch.domain.order.controller;

import jakarta.validation.Valid;
import kr.co.fastcampus.fastcatch.common.response.ResponseBody;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        orderService.createOrder(1L, orderRequest);
        return ResponseBody.ok();
    }
}
