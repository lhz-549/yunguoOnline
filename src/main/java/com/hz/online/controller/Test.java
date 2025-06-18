package com.hz.online.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hz.online.common.dto.TestDTO;
import com.hz.online.utils.AESEncryptionUtil;
import com.hz.online.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class Test {

    @Value("${custom.scheduled.cron}")
    private String cronExpression;

    @Value("${custom.scheduled.fixedRate}")
    private long fixedRate;

    @Value("${custom.scheduled.fixedDelay}")
    private long fixedDelay;

    @Value("${custom.scheduled.initialDelay}")
    private long initialDelay;

    // 使用 Cron 表达式配置的定时任务
//    @Scheduled(cron = "${custom.scheduled.cron}")
//    public void scheduleTaskUsingCronExpression() {
//        System.out.println("Cron 表达式定时任务执行：" + new java.util.Date()+"————"+cronExpression);
//    }

    // 使用 fixedRate 配置的定时任务
//    @Scheduled(fixedRateString = "${custom.scheduled.fixedRate}", initialDelayString = "${custom.scheduled.initialDelay}")
//    public void scheduleTaskWithFixedRate() {
//        System.out.println("固定速率定时任务执行：" + new java.util.Date()+
//                "————"+fixedRate+"————"+initialDelay);
//    }

    // 使用 fixedDelay 配置的定时任务
//    @Scheduled(fixedDelayString = "${custom.scheduled.fixedDelay}", initialDelayString = "${custom.scheduled.initialDelay}")
//    public void scheduleTaskWithFixedDelay() {
//        System.out.println("固定延迟定时任务执行：" + new java.util.Date()+"————"+fixedDelay);
//    }

    @GetMapping("/test")
    public static String add(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtils.verify(token);
        return "yunguoOnline!";
    }
    @GetMapping("/test2")
    public static String test2(){
        return "yunguoOnline!";
    }



    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptData1(@RequestParam String data) {
        try {
            log.info(data);
            String encryptedData = AESEncryptionUtil.encrypt(data);
            return ResponseEntity.ok(encryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Encryption error: " + e.getMessage());
        }
    }
    @PostMapping("/encrypt3")
    public ResponseEntity<String> encryptData2(@RequestBody JSONObject jsonObject) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(jsonObject);
            log.info("encrypt3:"+data);
            String encryptedData = AESEncryptionUtil.encrypt(data);
            return ResponseEntity.ok(encryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Encryption error: " + e.getMessage());
        }
    }
    @PostMapping("/encrypt2")
    public ResponseEntity<String> encryptData2(@RequestBody TestDTO testDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(testDTO);
            log.info("encrypt2:"+data);
            String encryptedData = AESEncryptionUtil.encrypt(data);
            return ResponseEntity.ok(encryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Encryption error: " + e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptData(@RequestParam String encryptedData) {
        try {
            String decryptedData = AESEncryptionUtil.decrypt(encryptedData);
            return ResponseEntity.ok(decryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Decryption error: " + e.getMessage());
        }
    }


}
