package com.hz.online;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.hz.online.mapper")
@EnableScheduling//定时任务
@EnableTransactionManagement//开启事务
public class YunguoOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunguoOnlineApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        //作用：定义了一个Spring Bean，用于创建和配置RestTemplate实例。
        //参数：使用了ClientHttpRequestFactory来配置RestTemplate，使其具有特定的请求配置。
        //为什么要这么用：通过配置RestTemplate，你可以设置一些默认的行为（如超时设置），以便在请求远程服务时更好地处理网络延迟或服务不可用等情况。
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        //作用：定义了一个Spring Bean，用于创建和配置ClientHttpRequestFactory实例。
        //设置：
        //setConnectTimeout(15000)：设置连接超时为15秒。连接超时是指从发起连接到建立连接的时间，如果超过这个时间还未建立连接，则会抛出java.net.ConnectException。
        //setReadTimeout(15000)：设置读取超时为15秒。读取超时是指从服务器读取数据的时间，如果超过这个时间还未收到数据，则会抛出java.net.SocketTimeoutException。
        //为什么要这么用：通过设置超时，可以防止程序因为网络问题而无限期地等待，从而提高系统的健壮性和用户体验。如果请求超时，程序可以及时采取补救措施（如重试、返回错误信息等）。
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(60000);
        factory.setReadTimeout(60000);
        return factory;
    }
}
