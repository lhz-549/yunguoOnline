package com.hz.online.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author haozi
 * @since 2024-07-22
 */
@TableName("cron_task_info")
@Data
public class CronTaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String cronExpression;

    private String taskData;

    private String taskDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    @Override
    public String toString() {
        return "CronTaskInfo{" +
            "id=" + id +
            ", cronExpression=" + cronExpression +
            ", taskData=" + taskData +
            ", taskDesc=" + taskDesc +
        "}";
    }
}
