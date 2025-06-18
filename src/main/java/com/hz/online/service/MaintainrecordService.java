package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Maintainrecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.entity.MaintainrecordProductDto;
import com.hz.online.entity.Plant;
import com.hz.online.mapper.MaintainrecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 养护记录表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-24
 */
@Service
@Slf4j
public class MaintainrecordService  {
    @Autowired
    MaintainrecordMapper maintainrecordMapper;

    public ResponseResult<List<Maintainrecord>> allRecordByUserid(String userid){
        QueryWrapper<Maintainrecord> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userid);
        List<Maintainrecord> maintainrecords = maintainrecordMapper.selectList(queryWrapper);
        return ResponseResult.success(maintainrecords);
    }

    public ResponseResult<List<Maintainrecord>> allRecordByUserid2(String userid){
        List<MaintainrecordProductDto> maintainrecords = maintainrecordMapper.allRecordByUseridwithpname(userid);
        return ResponseResult.success(maintainrecords);
    }
    public ResponseResult<List<Maintainrecord>> allRecordByUserid3(String userid){
        List<MaintainrecordProductDto> maintainrecords = maintainrecordMapper.allRecordByUseridwithpname2(userid);
        return ResponseResult.success(maintainrecords);
    }

    public ResponseResult selrecordnum(String userid){
        QueryWrapper<Maintainrecord> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userid);
        queryWrapper.ne("operation","能量球");
        Integer num = maintainrecordMapper.selectCount(queryWrapper);
        return ResponseResult.success(num);
    }

    public ResponseResult<List<Maintainrecord>> allRecordByuseridandpnameandoperation(String userid,String keywords){
        String[] s = keywords.split("[，,]");
        log.info("s数组情况："+ Arrays.toString(s));
        String pname="";
        String operate="";
        if(s.length>=2){
            pname=s[0];
            operate=s[1];
        } else if (s.length==1&&keywords.startsWith(" ")||!keywords.startsWith(",")) {
            pname=s[0];
            operate=null;
        } else if (s.length == 1 && keywords.startsWith(",")) {
            operate=s[0];
            pname=null;
        }
        log.info("pname："+ pname+"   "+"operate："+operate);
        List<MaintainrecordProductDto> maintainrecords = maintainrecordMapper.allRecordByuseridandpnameandoperation(userid,pname,operate);
        return ResponseResult.success(maintainrecords);
    }
}
