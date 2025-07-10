package com.hz.online.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// DTO 类保持不变，可加上 NumberFormat 等
@Data
public class OrderExcelDTO {
    @ExcelProperty("订单ID")
    private Long id;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("商品名称")
    private String productName;

    @ExcelProperty("金额")
    private BigDecimal amount;

    @ExcelProperty("下单时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
