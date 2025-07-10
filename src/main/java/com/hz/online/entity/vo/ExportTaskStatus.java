package com.hz.online.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExportTaskStatus {
    private String taskId;
    private String status; // PROCESSING, COMPLETED, FAILED
    private String fileUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long durationSeconds; // 单位：秒

}

