package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.UserCartDetails;
import com.hz.online.service.UserCartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-14
 */
@RestController
@RequestMapping("/user-cart-details")
public class UserCartDetailsController {
    @Autowired
    UserCartDetailsService userCartDetailsService;

    @PostMapping("/selectcartdetal")
    public ResponseResult<List<UserCartDetails>> selectcartdetal(@RequestParam("userid") String userid){
       return userCartDetailsService.cartdetal(userid);
    }

    @PostMapping("/selectcartdetalbyuseridandcartitemidstr")
    public ResponseResult<List<UserCartDetails>> selectcartdetalbyuseridandcartitemidstr(@RequestParam("userid") String userid,@RequestParam String cartitemidstr){
        return userCartDetailsService.selectcartdetalbyuseridandcartitemidstr(userid,cartitemidstr);
    }
}
