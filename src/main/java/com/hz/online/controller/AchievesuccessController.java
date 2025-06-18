package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.AchievementSuccessDTO;
import com.hz.online.service.AchievesuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 成就实现表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/achievesuccess")
public class AchievesuccessController {

    @Autowired
    AchievesuccessService achievesuccessService;

    @PostMapping("/selallachsuccessbyuserid")
    public ResponseResult<List<AchievementSuccessDTO>> selallachsuccessbyuserid(@RequestParam String userid){
        return achievesuccessService.achievesuccessService(userid);
    }

    @PostMapping("/selallachnumbyuserid")
    public ResponseResult selallachnumbyuserid(@RequestParam String userid){
        return achievesuccessService.selallachnumbyuserid(userid);
    }

}
