package com.hz.online.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSpecValuesDTO {

    private Integer id;

    private Integer productId;

    private Integer specId;

    private String specValue;

    private BigDecimal specPrice;

    private String specName;
}
