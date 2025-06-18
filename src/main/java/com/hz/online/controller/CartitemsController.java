package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.service.CartitemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 购物车项表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@RestController
@RequestMapping("/cartitems")
public class CartitemsController {
    @Autowired
    CartitemsService cartitemsService;

    @PostMapping("/addprotocartlist")
    public ResponseResult addCartitemsby(@RequestParam String userid, @RequestParam String pid, @RequestParam String quantity,
                                         @RequestParam String svid, @RequestParam String svname){
        int res = cartitemsService.addCartitemsby(userid, pid, quantity,svid,svname);
        if(res>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }
    @PostMapping("/addprotocartlistnew")
    public ResponseResult addCartitemsbynew(@RequestParam String userid, @RequestParam String pid, @RequestParam String quantity,
                                            @RequestParam String svid, @RequestParam String svname){
        int res = cartitemsService.addCartitemsbynew(userid, pid, quantity,svid,svname);
        if(res>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }

    @PostMapping("/cartitemsupdatenum")
    public ResponseResult cartitemsupdatenum( @RequestParam String cartitemid, @RequestParam String newQuantity){
        int res = cartitemsService.updateCartitemsbyuidandpid( cartitemid, newQuantity);
        if(res>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }
    @PostMapping("/delcartitems")
    public ResponseResult delcartitems(@RequestParam List<Integer>cartitemids){
        int res = cartitemsService.delcartitems(cartitemids);
        if(res>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }
    @PostMapping("/delcartitems2")
    public ResponseResult delcartitems2(@RequestParam String cartitemid){
        int res = cartitemsService.delcartitems2(cartitemid);
        if(res>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }
}
