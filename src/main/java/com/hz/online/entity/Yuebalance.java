package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 云果余额表
 * </p>
 *
 * @author haozi
 * @since 2024-07-10
 */
@Data
public class Yuebalance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    @TableId(value = "yue_id", type = IdType.AUTO)
    private Integer yueId;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 余额
     */
    private BigDecimal yueAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Integer getYueId() {
        return yueId;
    }

    public void setYueId(Integer yueId) {
        this.yueId = yueId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public BigDecimal getYueAmount() {
        return yueAmount;
    }

    public void setYueAmount(BigDecimal yueAmount) {
        this.yueAmount = yueAmount;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Yuebalance{" +
            "yueId=" + yueId +
            ", userId=" + userId +
            ", yueAmount=" + yueAmount +
            ", createTime=" + createTime +
        "}";
    }
}
