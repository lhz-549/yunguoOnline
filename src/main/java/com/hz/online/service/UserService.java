package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.User;
import com.hz.online.mapper.UserMapper;
import com.hz.online.utils.JwtUtils;
import com.hz.online.utils.MD5Utils;
import com.hz.online.utils.PWVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-06
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult<User> selectUser(){
        List<User> users = userMapper.selectList(null);
        return ResponseResult.success(users);
    }

    public ResponseResult adduser(User user){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone",user.getPhone());
        queryWrapper.eq("disable_mark",0);
        User user1 = userMapper.selectOne(queryWrapper);
        if(user1!=null){
            return ResponseResult.fail("该手机号对应的账号已经存在");
        } else{
            int isinsert = userMapper.insert(user);
            return ResponseResult.success("注册成功");
        }
    }

    public ResponseResult<String> updateuser(User user){
        int isupdateById = userMapper.updateById(user);
        return ResponseResult.success(isupdateById>0?"修改成功":"修改失败");
    }

    public ResponseResult<String> deleteuser(User user){
        int isupdateById = userMapper.deleteById(user);
        return ResponseResult.success(isupdateById>0?"删除成功":"删除失败");
    }

    public ResponseResult userlogin(User user){//手机号  /  账户、密码登录
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(user.getPhone()!=null){
            queryWrapper.eq("phone",user.getPhone());
        } else if (user.getAccount() != null) {
            queryWrapper.eq("account",user.getAccount());
        }
        queryWrapper.eq("password",user.getPassword());
        queryWrapper.eq("disable_mark",0);
        User user1 = userMapper.selectOne(queryWrapper);
        if(user1!=null){
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(user1.getId()));
            payload.put("name",user1.getName());
            String token = JwtUtils.getToken(payload);
            log.info("登陆成功");
            log.info("access_token:"+token);
            Map<String,Object> map =new HashMap<>();
            map.put("access_token",token);
            map.put("user",user1);
            return ResponseResult.success(map);
        }
        log.info("登陆失败");
        return ResponseResult.fail();
    }

    public ResponseResult userloginbyyzm(String email,String yzm){//验证码登录
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("email",email);
        queryWrapper.eq("disable_mark",0);
        User user1 = userMapper.selectOne(queryWrapper);
        if(user1!=null){
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(user1.getId()));
            payload.put("name",user1.getName());
            String token = JwtUtils.getToken(payload);
            if(stringRedisTemplate.hasKey(email)){
                String realyzm = stringRedisTemplate.opsForValue().get(email);
                if(realyzm.trim().equals(yzm.trim())){
                    log.info("登陆成功");
                    stringRedisTemplate.delete(email);
                    //return ResponseResult.success("生成token:"+token);
                    Map<String,Object> map =new HashMap<>();
                    map.put("access_token",token);
                    user1.setEmail(null);
                    user1.setPhone(null);
                    user1.setBirthday(null);
                    user1.setCreateTime(null);
                    user1.setPassword(null);
                    user1.setUpdateTime(null);
                    map.put("user",user1);
                    return ResponseResult.success(map);
                   // return ResponseResult.success(user1);
                    //登陆成功刷新其他列表
                    //···
                }else
                    log.info("登陆失败，验证码错误");
                return ResponseResult.fail("验证码错误");
            }else{
                return ResponseResult.fail("验证码已失效");
            }
        }else {
            LocalDateTime now = LocalDateTime.now();
            User user = new User();
            user.setEmail(email);
            user.setName("微信用户");
            String s1="https://i.111666.best/image/BMHZvtsK9q8OgQ35JMHJ1t.png";
            String s2="https://i.111666.best/image/60kQdD7ZIW56DacyTDOsWF.png";
            Random random =new Random();
            int randnum = random.nextInt(2);
            if(randnum==0){
                user.setHeadPortrait(s1);
            }else {
                user.setHeadPortrait(s2);
            }
            user.setAccount(email);
            String typw = "yg123456";
            String encrypt = MD5Utils.MD5addsalttolower(typw);
            user.setPassword(encrypt);
            user.setDisableMark(0);
            user.setCreateTime(now);
            user.setUpdateTime(now);
            int insert = userMapper.insert(user);
            String uid = String.valueOf(user.getId());
            while (uid.length()<6){
                uid = 0 + uid;
            }
            uid = "yg" + uid;
            log.info("uid:__________________"+uid);
            user.setAccount(uid);
            int updateById = userMapper.updateById(user);
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(user.getId()));
            payload.put("name",user.getName());
            String token = JwtUtils.getToken(payload);

            if(stringRedisTemplate.hasKey(email)){
                String realyzm = stringRedisTemplate.opsForValue().get(email);
                if(realyzm.trim().equals(yzm.trim())){
                    log.info("登陆成功");
                    stringRedisTemplate.delete(email);
                    //return ResponseResult.success("生成token:"+token);
                    Map<String,Object> map =new HashMap<>();
                    map.put("access_token",token);
                    user.setEmail(null);
                    user.setPhone(null);
                    user.setBirthday(null);
                    user.setCreateTime(null);
                    user.setPassword(null);
                    user.setUpdateTime(null);
                    map.put("user",user);
                    return ResponseResult.success(map);
                    // return ResponseResult.success(user1);
                    //登陆成功刷新其他列表
                    //···
                }else
                    log.info("登陆失败，验证码错误");
                return ResponseResult.fail("验证码错误");
            }else{
                return ResponseResult.fail("验证码已失效");
            }
        }
//        log.info("登陆失败");
//        return ResponseResult.fail();
    }

    public ResponseResult<User> seluserinfobyuserid(String uid){
        if (!uid.isEmpty()){
            User user = userMapper.selectById(uid);
            user.setId(null);
            user.setPassword(String.valueOf(8));
            return ResponseResult.success(user);
        }else
            return ResponseResult.fail("请传入有效uid");

    }

    private static final String PHONE_REGEX = "^1[3456789]\\d{9}$";
    public static boolean isValidPhone(String phoneNumber){
        return Pattern.matches(PHONE_REGEX,phoneNumber);
    }

    public ResponseResult updateuserinfobyuserid(User user){
        if (user.getId()!=null){
            String phone = user.getPhone();
            if (phone == null || phone.length() != 11 || !isValidPhone(phone)) {
                return ResponseResult.fail("请传入有效的手机号码");
            }
            User user2 = userMapper.selectById(user.getId());
            user2.setUpdateTime(LocalDateTime.now());
            int i = userMapper.updateById(user2);
            return ResponseResult.success(i);
        }else
            return ResponseResult.fail("请传入有效User");

    }
    public ResponseResult updatepwbyuserid(String uid, String oldpw, String newpw){
        if (uid!=null){
            User user = userMapper.selectById(uid);
            user.setUpdateTime(LocalDateTime.now());
            String password = user.getPassword();
            if(password.equals(MD5Utils.MD5addsalttolower(oldpw))){
                if(PWVerify.checkp(newpw).equals("ok")){
                    String encryptpw = MD5Utils.MD5addsalttolower(newpw);
                    user.setPassword(encryptpw);
                    int i = userMapper.updateById(user);
                    return ResponseResult.success(i);
                }else {
                    return ResponseResult.fail(PWVerify.checkp(newpw));
                }
            }else{
                return ResponseResult.fail("原始密码错误");
            }
        }else
            return ResponseResult.fail("请传入有效User");
    }



}
