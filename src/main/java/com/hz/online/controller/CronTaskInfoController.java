package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.CronTaskInfo;
import com.hz.online.service.CronTaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-07-22
 */
@RestController
@RequestMapping("/cron-task-info")
public class CronTaskInfoController {

    @Autowired
    private CronTaskInfoService cronTaskInfoService;

    @PostMapping("/selalllist")
    public ResponseResult<List<CronTaskInfo>> selalllist(){
        List<CronTaskInfo> cronTaskInfos = cronTaskInfoService.listTasksFromDatabase();
        return ResponseResult.success(cronTaskInfos);
    }
}
