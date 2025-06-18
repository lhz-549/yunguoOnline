package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.*;
import com.hz.online.service.OrderitemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 订单项表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-19
 */
@RestController
@RequestMapping("/orderitems")
@Slf4j
public class OrderitemsController {
    @Autowired
    OrderitemsService orderitemsService;

    @PostMapping("/allorderByuserid")
    public ResponseResult<List<OrderItemDto>> allorderByuserid(@RequestParam String userid){
        return orderitemsService.allorderByuserid(userid);
    }
    @PostMapping("/allorderByuseridandcondition")
    public ResponseResult<List<OrderItemDto>> allorderByuseridandcondition(@RequestParam String userid,@RequestParam String pname){
        return orderitemsService.allorderByuseridandcondition(userid,pname);
    }

    @PostMapping("/addorder")
    public ResponseResult addorder(@RequestBody AddOrderRequestDTO addOrderRequestDTO){

        return orderitemsService.addorder(addOrderRequestDTO);
    }

    @PostMapping("/findusedplant")
    public ResponseResult<List<FindPlantReqDTO>> findusedplant(@RequestParam String userid){
        return orderitemsService.findusedplant(userid);
    }

    @PostMapping("/updateorderstatus")
    public ResponseResult updateorderstatus(@RequestParam String orderid,@RequestParam String orderstate,@RequestParam String paystate,@RequestParam String paymethod){
        return orderitemsService.updateorderstatus(orderid,orderstate,paystate,paymethod);
    }

    @PostMapping("/selectorderinfobyorderid")
    public ResponseResult<OrderItemDto> selectorderinfobyorderid(@RequestParam String orderid){
        return orderitemsService.selectorderinfobyorderid(orderid);
    }

    @PostMapping("/updateorderstate")
    public ResponseResult updateorderstate(@RequestParam String orderid,@RequestParam String state){
        return orderitemsService.updateorderstate(orderid,state);
    }

    @PostMapping("/updateorderstrstate")
    public ResponseResult updateorderstrstate(@RequestParam String orderitemidstr,@RequestParam String state){
        return orderitemsService.updateorderstrstate(orderitemidstr,state);
    }

    @PostMapping("/deleteorderbyid")
    public ResponseResult deleteorderbyid(@RequestParam String orderid){
        return orderitemsService.deleteorderbyid(orderid);
    }

    @PostMapping("/deleteorderbyidstr")
    public ResponseResult deleteorderbyidstr(@RequestParam String orderitemidstr){
        return orderitemsService.deleteorderbyidstr(orderitemidstr);
    }
    //组合支付
    @PostMapping("/updateorderstatus2")
    public ResponseResult updateorderstatus2(@RequestParam String userid, @RequestParam String orderitemidstr, @RequestParam String orderstate, @RequestParam String paystate, @RequestParam String paymethod){
        return orderitemsService.updateorderstatus2(userid,orderitemidstr,orderstate,paystate,paymethod);
    }

    @PostMapping("/addorderzuhepay")
    public ResponseResult addorderzuhepay(@RequestParam String userid, @RequestParam String cartitemidstr){
        return orderitemsService.addorderzuhepay(userid,cartitemidstr);
    }
    @PostMapping("/addorderzuhepay2")
    public ResponseResult addorderzuhepay2(@RequestParam String userid, @RequestParam String cartitemidstr,@RequestParam String amount){
        return orderitemsService.addorderzuhepay2(userid,cartitemidstr,amount);
    }

    @PostMapping("/selectorderinfobyorderitemidstr")
    public ResponseResult<List<OrderItemDto>> selectorderinfobyorderitemidstr(@RequestParam String orderitemidstr){
        return orderitemsService.selectorderinfobyorderitemidstr(orderitemidstr);
    }

    @PostMapping("/selUsedRGAdoptNum")
    public ResponseResult<List<RGadoptDTO>> selUsedRGAdoptNum(@RequestParam String userid){
        return orderitemsService.selUsedRGAdoptNum(userid);
    }
}
