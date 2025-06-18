package com.hz.online.controller;

import com.hz.online.common.dto.ResponseResult;
import com.hz.online.service.EmailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.MessageFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class EmailSend {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    EmailSendService emailSendService;


    @PostMapping("/test")
    public void sendmessage() throws Exception {
        // 创建一个邮件消息
        MimeMessage message = javaMailSender.createMimeMessage();
        // 创建 MimeMessageHelper
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        // 发件人邮箱和名称
        helper.setFrom("1115406904@qq.com", "yunguoOnline");
        // 收件人邮箱
        helper.setTo("15706066707@163.com");
        // 邮件标题
        helper.setSubject("Hello");
        // 邮件正文，第二个参数表示是否是HTML正文
        helper.setText("Hello <strong> World</strong>！", true);
        // 发送
        javaMailSender.send(message);
    }

    @PostMapping("/sendEamil")
    public ResponseResult sendmessage2(@RequestParam("objto") String objto) throws Exception {//带附件

        String sendemail = emailSendService.sendemail(objto);
        if(!sendemail.isEmpty())
            return ResponseResult.success();
        else
            return ResponseResult.fail();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(random4());
        }
    }
    private static String random4() {
        return String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
    }


}
