package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Payments;
import com.hz.online.mapper.PaymentsMapper;
import com.hz.online.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 支付表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-07-08
 */
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    PaymentsService paymentsService;

    @PostMapping("/selpaymethodbyorderid")
    public ResponseResult<Payments> selpaymethodbyorderid(@RequestParam String orderid){
        return paymentsService.selpaymethodbyorderid(orderid);
    }

}
