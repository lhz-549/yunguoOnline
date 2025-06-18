package com.hz.online.mapper;

import com.hz.online.entity.Payments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 支付表 Mapper 接口
 * </p>
 *
 * @author haozi
 * @since 2024-07-08
 */
@Repository
public interface PaymentsMapper extends BaseMapper<Payments> {

}
