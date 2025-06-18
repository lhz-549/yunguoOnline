package com.hz.online.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemDto {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识订单项的ID
     */
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

    //0 已删除，1 创建， 2已付款，3 已种植，4 取消种植，5 退货中,
    // 6 已退货，7 已退款，8 待使用， 9 已使用，  10 已完成
    private String orderitemState;

    private LocalDateTime updateTime;

    private LocalDateTime finishTime;

    private String specvalueId;

    private String specvalueName;

    private String originalOrdernum;

    private Integer usedNum;

    private String remark;

    //产品id
    private Integer id;

    private String pName;

    /**
     * 价格
     */
    private BigDecimal pPrice;

    /**
     * 市场价格
     */
    private BigDecimal pMarketprice;

    /**
     * 商品图片
     */
    private String pImage;

    /**
     * 商品类别ID
     */
    private Integer pCategoryid;

    /**
     * 商品描述
     */
    private String pDescription;

    /**
     * 商品标签
     */
    private String badge;

    /**
     * 库存数量
     */
    private Integer stockQuantity;

    /**
     * 已售数量
     */
    private Integer selledQuantity;

    private String categoryName;

    private String categoryDescription;

}
