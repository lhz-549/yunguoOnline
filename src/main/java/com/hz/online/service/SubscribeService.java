package com.hz.online.service;

import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Subscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.entity.SubscribePlantProDTO;
import com.hz.online.mapper.SubscribeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 预约表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-29
 */
@Service
@Slf4j
public class SubscribeService  {
    @Autowired
    SubscribeMapper subscribeMapper;

    public ResponseResult updatemysubstate(String mysubid,String newstate){
        Subscribe sub= new Subscribe();
        sub.setSubId(Integer.valueOf(mysubid));
        sub.setSubState(Integer.valueOf(newstate));
        sub.setSubupdateTime(LocalDateTime.now());
        int insert = subscribeMapper.updateById(sub);
        if (insert>0)
            return ResponseResult.success(insert);
        else
            return ResponseResult.fail(insert);
    }

    public ResponseResult addsubscribeinfobyplant(Subscribe sub){
        sub.setSubcreateTime(LocalDateTime.now());
        sub.setSubupdateTime(LocalDateTime.now());
        sub.setSubState(1);
        int insert = subscribeMapper.insert(sub);
        if (insert>0)
            return ResponseResult.success(insert);
        else
            return ResponseResult.fail(insert);
    }

    public ResponseResult<List<SubscribePlantProDTO>> findAllByUserid(String userid){
        List<SubscribePlantProDTO> allByUserid = subscribeMapper.findAllByUserid(userid);
        return ResponseResult.success(allByUserid);
    }
}
