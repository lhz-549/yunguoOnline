package com.hz.online.entity;

import lombok.Data;

@Data
public class AchInfoDTO {


    private Integer achId;

    /**
     * 成就类型ID
     */
    private Integer achCateid;

    /**
     * 成就类型名称
     */
    private String achcateName;

    private String achImage;

    /**
     * 成就名称
     */
    private String achName;

    /**
     * 成就条件
     */
    private String achCondition;

    /**
     * 成就描述
     */
    private String achDesc;

    private Integer achExp;
}
