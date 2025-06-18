package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.constant.AllConstant;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.mapper.MaintainrecordMapper;
import com.hz.online.mapper.OrderitemsMapper;
import com.hz.online.mapper.PlantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@Service
@Slf4j
public class PlantService {

    @Autowired
    PlantMapper plantMapper;

    @Autowired
    MaintainrecordMapper maintainrecordMapper;

    @Autowired
    OrderitemsMapper orderitemsMapper;

    public ResponseResult<List<PlantprodesDto>> allplantbyuserid(String userid){
        List<PlantprodesDto> allplantinfobyuserid = plantMapper.allplantinfobyuserid(userid);
        if(allplantinfobyuserid!=null){
            return ResponseResult.success(allplantinfobyuserid);
        }else
            return ResponseResult.fail("该用户目前还没有种植");

    }

    public ResponseResult adoptgrowupvalue(String plantid,String value){
        Plant plant=new Plant();
        plant.setId(Integer.valueOf(plantid));
        plant.setGrowupValue(Integer.valueOf(value));
        int updateById = plantMapper.updateById(plant);
        if(updateById>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }

    public ResponseResult adoptgrowupvalue2(String plantid,String value,String valueps){
        Plant plant1 = plantMapper.selectById(plantid);
        Maintainrecord maintainrecord=new Maintainrecord();
        maintainrecord.setCurrentValue(Integer.valueOf(value));
        maintainrecord.setProductId(plant1.getProductId());
        maintainrecord.setUserId(plant1.getUserId());
        maintainrecord.setExecuteTime(LocalDateTime.now());
        maintainrecord.setPlantId(plant1.getId());
        switch (valueps){
            case AllConstant.Watering: {
                maintainrecord.setOperation("浇水");
                maintainrecord.setOperationValue("+"+AllConstant.Watering);
                maintainrecordMapper.insert(maintainrecord);
                break;
            }
            case AllConstant.Fertilization: {
                maintainrecord.setOperation("施肥");
                maintainrecord.setOperationValue("+"+AllConstant.Fertilization);
                maintainrecordMapper.insert(maintainrecord);
                break;
            }
            case AllConstant.Cutting: {
                maintainrecord.setOperation("修剪");
                maintainrecord.setOperationValue("+"+AllConstant.Cutting);
                maintainrecordMapper.insert(maintainrecord);
                break;
            }
            case AllConstant.EnergyBall: {
                maintainrecord.setOperation("能量球");
                maintainrecord.setOperationValue("+"+AllConstant.EnergyBall);
                maintainrecordMapper.insert(maintainrecord);
                break;
            }
            default: break;
        }
        Plant plant=new Plant();
        plant.setId(Integer.valueOf(plantid));
        plant.setGrowupValue(Integer.valueOf(value));
        int updateById = plantMapper.updateById(plant);
        if(updateById>0)
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }

    public ResponseResult selplantnum(String userid){
        QueryWrapper<Plant> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userid);
        Integer num = plantMapper.selectCount(queryWrapper);
        return ResponseResult.success(num);
    }

    public ResponseResult<List<PlantProductGrowDTO>> allplantinfobyuseridGrow(String userid){
        List<PlantProductGrowDTO> allplantinfobyuserid = plantMapper.allplantinfobyuseridGrow(userid);
        return ResponseResult.success(allplantinfobyuserid);
    }

    public ResponseResult addplantbyorderid(String orderid,String quantity){
        Orderitems orderitems = orderitemsMapper.selectById(orderid);
        Plant plant=new Plant();
        plant.setUserId(orderitems.getUserId());
        plant.setProductId(orderitems.getProductId());
        plant.setGrowupValue(0);
        plant.setCreateTime(LocalDateTime.now());
        plant.setUpdateTime(LocalDateTime.now());
        plant.setState("0");
        plant.setOrderitemId(Integer.valueOf(orderid.trim()));
        Integer num = Integer.valueOf(quantity);
        int insert = 1;
        for (int i = 0; i < num; i++) {
            int insert1 = plantMapper.insert(plant);
            insert = insert < insert1 ? insert : insert1;
        }
        if(insert>0){
            Integer quantity1 = orderitems.getUsedNum()-Integer.valueOf(quantity.trim());
            orderitems.setUsedNum(quantity1);
            int updateById = orderitemsMapper.updateById(orderitems);
            log.info(updateById>0?"增加种植处理订单可用数量处理成功":"增加种植处理订单可用数量处理失败");
            return ResponseResult.success(insert);
        }
        else
            return ResponseResult.fail();
    }

}
