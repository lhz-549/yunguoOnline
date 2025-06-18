package com.hz.online.controller;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.User;
import com.hz.online.service.UserService;
import com.hz.online.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-06
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseResult<User> selectall(){
        return userService.selectUser();
    }
    @PostMapping("/adduser")
    public ResponseResult adduser(@RequestBody User user){
        return userService.adduser(user);
    }
    @PostMapping("/updateuser")
    public ResponseResult updateuser(@RequestBody User user){
        return userService.updateuser(user);
    }
    @PostMapping("/deleteuser")
    public ResponseResult deleteuser(@RequestBody User user){
        return userService.deleteuser(user);
    }
    @PostMapping("/login")
    public ResponseResult userlogin(@RequestBody User user){
        return userService.userlogin(user);
    }

    @PostMapping("/loginbyyzm")
    public ResponseResult userloginbyyzm(@RequestParam("email") String email, @RequestParam("code") String code){
        return userService.userloginbyyzm(email, code);
    }

    @PostMapping("/persetoken")
    public ResponseResult persetoken(String token){
        log.info("当前token为：[{}]",token);
        Map<String,Object> map = new HashMap<>();
        try {
            // 验证令牌
            DecodedJWT verify = JwtUtils.verify(token);
            String id = verify.getClaim("id").asString();
            String name = verify.getClaim("name").asString();
            map.put("state",true);
            map.put("msg","请求成功");
            map.put("id",id);
            map.put("name",name);
            return ResponseResult.success(map);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","无效签名！");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg","算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","token无效！");
        }
        map.put("state",false);
        return ResponseResult.fail(map);
    }
    @PostMapping("/seluserinfobyuserid")
    public ResponseResult<User> seluserinfobyuserid(@RequestParam String uid){

        return userService.seluserinfobyuserid(uid);
    }

    @PostMapping("/updateuserinfobyuserid")
    public ResponseResult updateuserinfobyuserid(@RequestBody User user){

        return userService.updateuserinfobyuserid(user);
    }
    @PostMapping("/updatepwbyuserid")
    public ResponseResult updatepwbyuserid(@RequestParam String uid,@RequestParam String oldpw,@RequestParam String newpw){

        return userService.updatepwbyuserid(uid,oldpw,newpw);
    }

}
