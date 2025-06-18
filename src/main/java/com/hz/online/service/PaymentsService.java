package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Payments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.mapper.PaymentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 支付表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-07-08
 */
@Service
public class PaymentsService {

    @Autowired
    PaymentsMapper paymentsMapper;

    public ResponseResult<Payments> selpaymethodbyorderid(String orderid){
        QueryWrapper<Payments> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_id",orderid);
        Payments payments = paymentsMapper.selectOne(queryWrapper);
        return ResponseResult.success(payments);
    }

}
