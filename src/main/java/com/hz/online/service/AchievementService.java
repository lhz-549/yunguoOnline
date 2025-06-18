package com.hz.online.service;

import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.AchInfoDTO;
import com.hz.online.entity.Achievement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.entity.AchievementSuccessDTO;
import com.hz.online.mapper.AchievementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 成就表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@Service
@Slf4j
public class AchievementService  {
    @Autowired
    AchievementMapper achievementMapper;

    public ResponseResult<List<AchievementSuccessDTO>> selallach(){
        List<AchInfoDTO> res = achievementMapper.selallach();
        return ResponseResult.success(res);
    }

}
