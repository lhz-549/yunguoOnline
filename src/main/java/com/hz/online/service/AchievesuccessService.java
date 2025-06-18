package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.AchInfoDTO;
import com.hz.online.entity.AchievementSuccessDTO;
import com.hz.online.entity.Achievesuccess;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.mapper.AchievesuccessMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 成就实现表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@Service
@Slf4j
public class AchievesuccessService  {

    @Autowired
    AchievesuccessMapper achievesuccessMapper;

    public ResponseResult<List<AchievementSuccessDTO>> achievesuccessService(String userid){
        List<AchievementSuccessDTO> res = achievesuccessMapper.achievesuccessService(userid);
        return ResponseResult.success(res);
    }


    public ResponseResult selallachnumbyuserid(String userid){
        QueryWrapper<Achievesuccess> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        int res = achievesuccessMapper.selectCount(queryWrapper);
        return ResponseResult.success(res);
    }
}
