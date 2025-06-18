package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 地区编码表
 * </p>
 *
 * @author haozi
 * @since 2024-06-07
 */
@TableName("dic_district")
@Data
public class DicDistrict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地区编码
     */
    private String addressCode;

    /**
     * 地区名称
     */
    private String addressName;

    /**
     * 父地区编码
     */
    private String parentAddressCode;

    /**
     * 级别
     */
    private Integer level;

}
