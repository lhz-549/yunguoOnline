package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Achievement;
import com.hz.online.entity.AchievementSuccessDTO;
import com.hz.online.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 成就表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Autowired
    AchievementService achievementService;

    @PostMapping("/selallach")
    public ResponseResult<List<AchievementSuccessDTO>> selallach(){
        return achievementService.selallach();
    }

}
