package com.hz.online.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hz.online.entity.Orders;
import com.hz.online.mapper.OrdersMapper;
import com.hz.online.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
