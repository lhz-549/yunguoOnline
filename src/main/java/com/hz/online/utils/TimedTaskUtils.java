package com.hz.online.utils;

import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.CronTaskInfo;
import com.hz.online.entity.Orderitems;
import com.hz.online.entity.User;
import com.hz.online.mapper.CronTaskInfoMapper;
import com.hz.online.mapper.OrderitemsMapper;
import com.hz.online.service.OrderitemsService;
import com.hz.online.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimedTaskUtils {

    @Autowired
    private UserService userService;

    @Autowired
    OrderitemsMapper orderitemsMapper;

    @Autowired
    OrderitemsService orderitemsService;

    @Autowired
    private CronTaskInfoMapper cronTaskInfoMapper;

    public String testAddUser(CronTaskInfo task){
        if ("Insert new user data once".equals(task.getTaskDesc())) {
            User user = new User();
            user.setName("定时任务增加");
            user.setEmail("Scheduled@qq.com");
            user.setAccount("yg00000x");
            user.setPassword("yg123456");
            user.setPhone("12345678999");
            ResponseResult adduser = userService.adduser(user);
            if (adduser.getData().equals("注册成功")) {
                // 移除任务记录
                if (task.getId() != null) {
                    cronTaskInfoMapper.deleteById(task.getId());
                    System.out.println("Removed one-time task with ID: " + task.getId());
                }
                return "Inserted new user: " + user.getName() + user.getPhone();
            } else
                return "Error Inserted new user ——" +"原因："+ adduser.getData();
        } else {
            return "匹配不到对应的字段";
        }
    }

    public String cancelorder(CronTaskInfo task){
        if ("10minlaterAutoCancelOrder".equals(task.getTaskDesc())) {
            Integer orderid = Integer.valueOf(task.getTaskData());
            Orderitems orderitems = orderitemsMapper.selectById(orderid);
            orderitems.setUpdateTime(LocalDateTime.now());
            orderitems.setOrderitemState("0");
            int updateById = orderitemsMapper.updateById(orderitems);
            if (updateById>0) {
                // 移除任务记录
                if (task.getId() != null) {
                    cronTaskInfoMapper.deleteById(task.getId());
                    System.out.println("Removed one-time task with ID: " + task.getId());
                }
                return "Success update order: id = " + orderid;
            } else
                return "Error update order ";
        } else {
            return "匹配不到对应的字段";
        }
    }
}
