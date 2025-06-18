package com.hz.online.service;

import com.hz.online.entity.CronTaskInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.mapper.CronTaskInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haozi
 * @since 2024-07-22
 */
@Service
public class CronTaskInfoService {
    @Autowired
    private CronTaskInfoMapper cronTaskInfoMapper;

    public List<CronTaskInfo> listTasksFromDatabase() {
        List<CronTaskInfo> cronTaskInfos = cronTaskInfoMapper.selectList(null);
        return cronTaskInfos;
    }
}
