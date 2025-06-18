package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付表
 * </p>
 *
 * @author haozi
 * @since 2024-07-08
 */
@Data
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识支付的ID
     */
    @TableId(value = "pay_id", type = IdType.AUTO)
    private Integer payId;

    /**
     * 关联订单ID
     */
    private Integer orderId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 支付状态 0 作废，1 已退款，2 已支付
     */
    private String payStatus;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付日期
     */
    private LocalDateTime payDate;

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "Payments{" +
            "payId=" + payId +
            ", orderId=" + orderId +
            ", payMethod=" + payMethod +
            ", payStatus=" + payStatus +
            ", payAmount=" + payAmount +
            ", payDate=" + payDate +
        "}";
    }
}
