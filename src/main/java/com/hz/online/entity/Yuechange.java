package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 余额变动记录表
 * </p>
 *
 * @author haozi
 * @since 2024-07-10
 */
@Data
public class Yuechange implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    @TableId(value = "change_id", type = IdType.AUTO)
    private Integer changeId;

    /**
     * 关联用户ID
     */
    private Integer userId;

    /**
     * 余额表ID
     */
    private Integer yueId;

    /**
     * 变动前余额
     */
    private BigDecimal yueBefore;

    /**
     * 余额变动值
     */
    private String yueChange;

    /**
     * 变动前余额
     */
    private BigDecimal yueAfter;

    /**
     * 变更时间
     */
    private LocalDateTime changeTime;

    /**
     * 余额变动备注
     */
    private String yueRemark;

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getYueId() {
        return yueId;
    }

    public void setYueId(Integer yueId) {
        this.yueId = yueId;
    }
    public BigDecimal getYueBefore() {
        return yueBefore;
    }

    public void setYueBefore(BigDecimal yueBefore) {
        this.yueBefore = yueBefore;
    }
    public String getYueChange() {
        return yueChange;
    }

    public void setYueChange(String yueChange) {
        this.yueChange = yueChange;
    }
    public BigDecimal getYueAfter() {
        return yueAfter;
    }

    public void setYueAfter(BigDecimal yueAfter) {
        this.yueAfter = yueAfter;
    }
    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }
    public String getYueRemark() {
        return yueRemark;
    }

    public void setYueRemark(String yueRemark) {
        this.yueRemark = yueRemark;
    }

    @Override
    public String toString() {
        return "Yuechange{" +
            "changeId=" + changeId +
            ", userId=" + userId +
            ", yueId=" + yueId +
            ", yueBefore=" + yueBefore +
            ", yueChange=" + yueChange +
            ", yueAfter=" + yueAfter +
            ", changeTime=" + changeTime +
            ", yueRemark=" + yueRemark +
        "}";
    }
}
