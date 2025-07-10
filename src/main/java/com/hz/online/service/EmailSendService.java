package com.hz.online.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.MessageFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmailSendService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public String sendemail(String objto) throws Exception {
        // 创建一个邮件消息
        MimeMessage message = javaMailSender.createMimeMessage();
        // 创建 MimeMessageHelper，指定 boolean multipart 参数为 true
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        // 发件人邮箱和名称
        helper.setFrom("1115406904@qq.com", "YGOnline");
        // 收件人邮箱
        //helper.setTo("15706066707@163.com");
        //helper.setTo("chong_yn1@163.com");
        helper.setTo(objto);
        // 邮件标题
        helper.setSubject("验证码");

        //验证码
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));

        // 邮件正文，第二个参数表示是否是HTML正文
        helper.setText(buildContent(code)+"<br/> 请查看您的验证码！", true);
//        helper.setText(buildContent(code)+"<br/> 勿忘查看您的附件壁纸！", true);
        // 添加一个附件，指定附件名称、文件的 Inputstream 流 以及 Content-Type
//        helper.addAttachment("test.txt",
//                () -> Files.newInputStream(Paths.get(
//                        "F:\\zhuomian\\新建文本文档.txt")),
//                "application/octet-stream");
//        helper.addAttachment("test.jpg",new File("G:\\图\\水果\\ds3.jpg"));
        // 发送
        javaMailSender.send(message);
        if(!stringRedisTemplate.hasKey(objto))
            stringRedisTemplate.opsForValue().set(objto,code,3,TimeUnit.MINUTES);
        log.info(objto+"所对应的验证码："+code);
        return code+"0549";
    }

    public String buildContent(String title) {
        //加载邮件html模板
        Resource resource = new ClassPathResource("mailtemplate.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败{}", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }
}
