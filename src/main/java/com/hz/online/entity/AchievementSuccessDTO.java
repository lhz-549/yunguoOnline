package com.hz.online.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AchievementSuccessDTO {

    private Integer asId;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 关联成就表ID
     */
    private Integer achId;

    /**
     * 成就实现时间
     */
    private LocalDateTime achieveTime;

    /**
     * 成就类型ID
     */
    private Integer achCateid;

    /**
     * 成就类型名称
     */
    private String achcateName;

    /**
     * 成就名称
     */
    private String achName;

    private String achImage;

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
