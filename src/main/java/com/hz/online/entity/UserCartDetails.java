package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author haozi
 * @since 2024-06-14
 */
@TableName("user_cart_details")
@Data
public class UserCartDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联用户ID
     */
    private Integer userId;

    private Integer cartitemId;

    private LocalDateTime createTime;

    /**
     * 关联商品ID
     */
    private Integer productId;

    private String specvalueId;

    private String specvalueName;

    /**
     * 商品名称
     */
    private String pName;

    /**
     * 商品图片
     */
    private String pImage;

    /**
     * 商品数量
     */
    private Integer quantity;

    private BigDecimal pPrice;
    /**
     * 价格
     */
    private BigDecimal unitPrice;

    /**
     * 商品价格
     */
    private BigDecimal totalPrice;

}
