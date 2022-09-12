package com.zb.WebSample.controller;

import com.zb.WebSample.WebSampleApplication;
import com.zb.WebSample.dto.ErrorResponse;
import com.zb.WebSample.exception.ErrorCode;
import com.zb.WebSample.exception.WebSampleException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestController
public class SampleController {

    //    @RequestMapping(value="/order/1", method= RequestMethod.GET)
    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable("orderId") String id) throws IllegalAccessException, SQLIntegrityConstraintViolationException {
        log.info("Get some order : " + id);

        if ("500".equals(id)) {
            throw new WebSampleException(ErrorCode.TOO_BIG_ID_ERROR, "500 is not valid orderId");
        }

        if("3".equals(id)){
            throw new WebSampleException(ErrorCode.TOO_SMALL_ID_ERROR, "3 is too small orderId");
        }

        if("4".equals(id)){
            throw new SQLIntegrityConstraintViolationException("Duplicated insertion was tried");
        }

        return "orderId:" + id + ", orderAmount:1000";
    }

    @GetMapping("/order")
    public String getOrderWIthRequestParam(
            @RequestParam(value = "orderId", required = false, defaultValue = "defaultId") String id,
            @RequestParam("orderAmount") Integer amount) {
        log.info("Get some order : " + id + ", amount : " + amount);
        return "orderId:" + id + ", orderAmount" + amount;
    }

    @DeleteMapping("/order/{orderId}")
    public String deleteOrder(@PathVariable("orderId") String id) {
        log.info("Delete some order : " + id);
        return "Delete orderId:" + id;
    }

    @PostMapping("/order")
    public String createOrder(
            @RequestBody CreateOrderRequest createOrderRequest,
            @RequestHeader String userAccountId) {
        log.info("Create order" + createOrderRequest + ", userAccountId: " + userAccountId);
        return "orderId:" + createOrderRequest.getOrderId()
                + ", orderAmount:" + createOrderRequest.getOrderAmount() + userAccountId;
    }

    @Data
    public static class CreateOrderRequest {
        private String orderId;
        private Integer orderAmount;
    }
}
