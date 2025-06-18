package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 成就表
 * </p>
 *
 * @author haozi
 * @since 2024-06-26
 */
@Data
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    @TableId(value = "ach_id", type = IdType.AUTO)
    private Integer achId;

    /**
     * 成就类型ID
     */
    private Integer achCateid;

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
