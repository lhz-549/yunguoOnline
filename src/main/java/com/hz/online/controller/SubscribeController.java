package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Subscribe;
import com.hz.online.entity.SubscribePlantProDTO;
import com.hz.online.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 预约表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-29
 */
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Autowired
    SubscribeService subscribeService;

    @PostMapping("/addsubscribeinfobyplant")
    public ResponseResult addsubscribeinfobyplant(@RequestBody Subscribe sub){
        return  subscribeService.addsubscribeinfobyplant(sub);
    }

    @PostMapping("/updatemysubstate")
    public ResponseResult updatemysubstate(@RequestParam String mysubid,@RequestParam String newstate ){
        return subscribeService.updatemysubstate(mysubid,newstate);
    }

    @PostMapping("/findAllByUserid")
    public ResponseResult<List<SubscribePlantProDTO>> findAllByUserid(@RequestParam String userid){
        return subscribeService.findAllByUserid(userid);
    }
}
