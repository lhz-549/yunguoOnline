package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Yuebalance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.entity.Yuechange;
import com.hz.online.mapper.YuebalanceMapper;
import com.hz.online.mapper.YuechangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 云果余额表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-07-10
 */
@Service
public class YuebalanceService {

    @Autowired
    YuebalanceMapper yuebalanceMapper;

    @Autowired
    YuechangeMapper yuechangeMapper;

    public ResponseResult<Yuebalance> selectyuebyuserid( String userid){
        QueryWrapper<Yuebalance> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        Yuebalance yuebalance = yuebalanceMapper.selectOne(queryWrapper);
        return ResponseResult.success(yuebalance);
    }

    public ResponseResult adoptyue( String userid, String change){
        QueryWrapper<Yuebalance> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        Yuebalance yuebalance = yuebalanceMapper.selectOne(queryWrapper);
        BigDecimal yueAmount = yuebalance.getYueAmount();
        //正加负减
        BigDecimal changebd = new BigDecimal(Integer.valueOf(change));
        BigDecimal afteradoptamout = yueAmount.add(changebd);
//        if(change.startsWith("-")){
//            afteradoptamout = yueAmount.subtract(new BigDecimal(change.substring(1)));
//        }else if(change.startsWith("+")) {
//            afteradoptamout = yueAmount.add(new BigDecimal(change.substring(1)));
//        }else {
//            afteradoptamout = yueAmount.add(new BigDecimal(change));
//        }

        yuebalance.setYueAmount(afteradoptamout);
        int updateById = yuebalanceMapper.updateById(yuebalance);
        Yuechange yuechange =new Yuechange();

        yuechange.setUserId(Integer.valueOf(userid));
        yuechange.setYueId(yuebalance.getYueId());
        yuechange.setYueBefore(yueAmount);
        yuechange.setYueChange(change);
        yuechange.setYueAfter(afteradoptamout);
        yuechange.setChangeTime(LocalDateTime.now());
        String str = "";

        if(new BigDecimal(change).compareTo(BigDecimal.ZERO) < 0){
            str = "——提现";
        }else if(new BigDecimal(change).compareTo(BigDecimal.ZERO) > 0){
            str = "——充值";
        }
        yuechange.setYueRemark("系统自动生成"+str);
        int insert = yuechangeMapper.insert(yuechange);
        insert= updateById>insert? insert:updateById;
        return ResponseResult.success(insert);
    }

    public static void main(String[] args) {
        String str = "-500";
        Integer integer = Integer.valueOf(str);
        System.out.println(integer);
    }
}
