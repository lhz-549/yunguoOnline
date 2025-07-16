package com.hz.online.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Data
@Configuration
@ConfigurationProperties(prefix = "threadpool")
public class ThreadPoolConfig {

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;

    private int keepAliveSeconds;

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor myThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("MyPool-");  // 设置线程名前缀，方便排查
        executor.initialize();
        return executor;
    }
}
