package com.hz.online.entity;

import lombok.Data;

@Data
public class FindPlantReqDTO {
    private Integer orderitemId;
    private String  orderNum;
    private Integer usedNum;
    private Integer quantity;
    private String specvalueName;
    private String pName;
    private String pImage;
}
