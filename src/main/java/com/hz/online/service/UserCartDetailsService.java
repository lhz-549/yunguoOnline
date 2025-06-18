package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.UserCartDetails;
import com.hz.online.mapper.UserCartDetailsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-14
 */
@Service
@Slf4j
public class UserCartDetailsService{
    @Autowired
    UserCartDetailsMapper userCartDetailsMapper;

    public ResponseResult<List<UserCartDetails>> cartdetal(String userid){
        List<UserCartDetails> userCartDetails = userCartDetailsMapper.cartdetal2(userid);
        return ResponseResult.success(userCartDetails);
    }

    public ResponseResult<List<UserCartDetails>> selectcartdetalbyuseridandcartitemidstr( String userid,  String cartitemidstr){
        //log.info(cartitemidstr);
        String[] cartItemIdsStrArray = cartitemidstr.split(",");
        List<Integer> cartItemIdsList = Arrays.stream(cartItemIdsStrArray)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //log.info(cartItemIdsList.toString());
        List<UserCartDetails> cartDetails = userCartDetailsMapper.selectcartdetalbyuseridandcartitemidstr(userid, cartItemIdsList);

        return ResponseResult.success(cartDetails);
    }
}
