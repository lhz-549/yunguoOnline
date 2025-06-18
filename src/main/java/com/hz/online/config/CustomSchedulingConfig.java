package com.hz.online.config;

import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.CronTaskInfo;
import com.hz.online.entity.User;
import com.hz.online.mapper.CronTaskInfoMapper;
import com.hz.online.service.CronTaskInfoService;
import com.hz.online.service.UserService;
import com.hz.online.utils.MD5Utils;
import com.hz.online.utils.TimedTaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class CustomSchedulingConfig implements SchedulingConfigurer {

    @Autowired
    private CronTaskInfoService cronTaskInfoService;
    @Autowired
    private TimedTaskUtils timedTaskUtils;
    private ThreadPoolTaskScheduler scheduler;



    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        // 创建自定义的线程池任务调度器
        scheduler = threadPoolTaskScheduler();
        // 设置调度器到任务注册表
        taskRegistrar.setTaskScheduler(scheduler);
        //log.info("定时任务配置开始");
        reloadScheduledTasks();
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        //log.info("ThreadPoolTaskScheduler开始工作");
        // 创建一个新的ThreadPoolTaskScheduler实例
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // 设置线程池的大小
        scheduler.setPoolSize(5);
        // 设置线程名称的前缀
        scheduler.setThreadNamePrefix("scheduled-task-");
        // 设置调度器在关闭前的等待时间（秒）
        scheduler.setAwaitTerminationSeconds(60);
        // 设置拒绝执行的任务处理器，当线程池满时调用
        scheduler.setRejectedExecutionHandler((r, executor) ->
                System.err.println("定时任务被拒绝: " + r.toString()));
        // 设置错误处理器，处理定时任务执行过程中抛出的异常
        scheduler.setErrorHandler(e ->
                System.err.println("定时任务发生错误: " + e.getMessage()));
        return scheduler;
    }

    @Scheduled(fixedRate = 300000) // 每300秒执行一次 /5min
    public void reloadScheduledTasks() {
        //log.info("检查并重新加载定时任务配置");
        if (scheduler != null) {
            // 清除当前调度器中的所有任务
            //log.info("当前调度器不为空");
            // 停止当前调度器中的所有任务
            scheduler.getScheduledThreadPoolExecutor().getQueue().clear();
        }
        // 重新创建调度器
        scheduler = threadPoolTaskScheduler();
        List<CronTaskInfo> tasksFromDB = cronTaskInfoService.listTasksFromDatabase();
        if(!tasksFromDB.isEmpty()){
            log.info(tasksFromDB.toString());
            // 重新添加任务
            for (CronTaskInfo task : tasksFromDB) {
                Runnable taskRunner = createTaskRunner(task);
                CronTrigger cronTrigger = new CronTrigger(task.getCronExpression());
                if (scheduler != null) {
                    scheduler.schedule(taskRunner, cronTrigger);
                    log.info("定时任务已加入定时处理，正在处理中");
//                    log.info("有需要待执行的定时任务列表，但当前没有需要执行的定时任务");
                }
            }
        }else{
            log.info("没有需要执行的任务");
        }
    }

    private Runnable createTaskRunner(CronTaskInfo task) {
        return () -> {
            timedTaskUtils.testAddUser(task);
            timedTaskUtils.cancelorder(task);
        };
    }
}

