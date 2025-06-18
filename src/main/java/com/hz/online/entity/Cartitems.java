package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车项表
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@Data
public class Cartitems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识购物车项的ID
     */
    @TableId(value = "cartitem_id", type = IdType.AUTO)
    private Integer cartitemId;

    /**
     * 关联购物车ID
     */
    private Integer cartId;

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String specvalueId;

    private String specvalueName;
}
