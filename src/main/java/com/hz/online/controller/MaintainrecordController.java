package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Maintainrecord;
import com.hz.online.service.MaintainrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 养护记录表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/maintainrecord")
public class MaintainrecordController {
    @Autowired
    MaintainrecordService maintainrecordService;

    @PostMapping("/allRecordByUserid")
    public ResponseResult<List<Maintainrecord>> allRecordByUserid(String userid){
        return maintainrecordService.allRecordByUserid(userid);
    }

    @PostMapping("/allRecordByUserid2")
    public ResponseResult<List<Maintainrecord>> allRecordByUserid2(String userid){
        return maintainrecordService.allRecordByUserid2(userid);
    }
    @PostMapping("/selrecordnum")
    public ResponseResult selrecordnum(String userid){
        return maintainrecordService.selrecordnum(userid);
    }

    @PostMapping("/allRecordByuidpnameoper")
    public ResponseResult<List<Maintainrecord>> allRecordByuseridandpnameandoperation(String userid,String keywords){
        return maintainrecordService.allRecordByuseridandpnameandoperation(userid,keywords);
    }

    @PostMapping("/allRecordByUserid3")
    public ResponseResult<List<Maintainrecord>> allRecordByUserid3(String userid){
        return maintainrecordService.allRecordByUserid3(userid);
    }
}
