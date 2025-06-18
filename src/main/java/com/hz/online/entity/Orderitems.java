package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单项表
 * </p>
 *
 * @author haozi
 * @since 2024-06-19
 */

@Data
public class Orderitems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识订单项的ID
     */
    @TableId(value = "orderitem_id", type = IdType.AUTO)
    private Integer orderitemId;


    private Integer userId;

    private String  orderNum;
    /**
     * 关联商品ID
     */
    private Integer productId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品价格
     */
    private BigDecimal price;

    //0 已删除，1 创建， 2已付款，3 已种植，4 取消种植，5 退货中,  6 已退货，7 已退款，8 待使用， 9 已使用，  10 已完成
    private String orderitemState;

    private LocalDateTime updateTime;

    private LocalDateTime finishTime;

    private String specvalueId;

    private String specvalueName;

    private String originalOrdernum;

    private Integer usedNum;

    private String remark;

}
