package com.hz.online.test;

import com.hz.online.utils.JwtUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/testToken")
public class TestToken {

    @RequestMapping("/getToken")
    public String getToken() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "admin");
        map.put( "password", "123456");
        String token = JwtUtils.getToken(map);
        return token;
    }
    @RequestMapping("/testisvalid")
    public String testisvalid() {
        return "testisvalid is succseeful!";
    }
}
