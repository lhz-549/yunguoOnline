package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Yuebalance;
import com.hz.online.mapper.YuebalanceMapper;
import com.hz.online.service.YuebalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 云果余额表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-07-10
 */
@RestController
@RequestMapping("/yuebalance")
public class YuebalanceController {

    @Autowired
    YuebalanceService yuebalanceService;

    @PostMapping("/selectyuebyuserid")
    public ResponseResult<Yuebalance> selectyuebyuserid(@RequestParam String userid){
        return yuebalanceService.selectyuebyuserid(userid);
    }

    @PostMapping("/adoptyue")
    public ResponseResult adoptyue(@RequestParam String userid, @RequestParam String change){
        return yuebalanceService.adoptyue(userid,change);
    }

}
