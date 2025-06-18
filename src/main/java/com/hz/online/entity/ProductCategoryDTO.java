package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductCategoryDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识商品的ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
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
    private Integer selled_quantity;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private Integer categoryId;

    private String categoryName;

    private String categoryDescription;
}
