package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.FindPlantReqDTO;
import com.hz.online.entity.PlantProductGrowDTO;
import com.hz.online.entity.PlantprodesDto;
import com.hz.online.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/plant")
public class PlantController {

    @Autowired
    PlantService plantService;

    @PostMapping("/allplantinfobyuserid")
    public ResponseResult<List<PlantprodesDto>> allplantinfobyuserid(String userid){
        return plantService.allplantbyuserid(userid);
    }
    @PostMapping("/adoptgrowupvalue")
    public ResponseResult adoptgrowupvalue(String plantid,String value){
        return plantService.adoptgrowupvalue(plantid,value);
    }
    @PostMapping("/adoptgrowupvalue2")
    public ResponseResult adoptgrowupvalue2(String plantid,String value,String valueps){
        return plantService.adoptgrowupvalue2(plantid,value,valueps);
    }

    @PostMapping("/selplantnum")
    public ResponseResult selplantnum(String userid){
        return plantService.selplantnum(userid);
    }

    @PostMapping("/allplantinfobyuseridGrow")
    public ResponseResult<List<PlantProductGrowDTO>> allplantinfobyuseridGrow(String userid){
        return plantService.allplantinfobyuseridGrow(userid);
    }

    @PostMapping("/addplantbyorderid")
    public ResponseResult addplantbyorderid(@RequestParam String orderid,@RequestParam String quantity){
        return plantService.addplantbyorderid(orderid,quantity);
    }
}
