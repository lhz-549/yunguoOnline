package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.entity.Cart;
import com.hz.online.entity.Cartitems;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.entity.Products;
import com.hz.online.entity.UserCartDetails;
import com.hz.online.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车项表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-17
 */
@Service
@Slf4j
public class CartitemsService {
    @Autowired
    CartitemsMapper cartitemsMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductsMapper productsMapper;

    @Autowired
    ProductSpecValuesMapper productSpecValuesMapper;

    @Autowired
    UserCartDetailsMapper userCartDetailsMapper;

    public int addCartitemsby(String userid,String pid,String quantity,String svid,String svname){
        int res = 0;
        QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        queryWrapper.eq("state","0");
        Cart cart = cartMapper.selectOne(queryWrapper);
        if(cart == null){
            Cart cart1=new Cart();
            cart1.setUserId(Integer.valueOf(userid));
            cart1.setState("0");
            int insert = cartMapper.insert(cart1);
            log.info(insert>0?"插入成功":"插入失败");
        }
        cart = cartMapper.selectOne(queryWrapper);

        QueryWrapper<Cartitems> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("cart_id",cart.getCartId());
        queryWrapper1.eq("product_id",pid.trim());
        queryWrapper1.eq("specvalue_id",svid);
        Cartitems one = cartitemsMapper.selectOne(queryWrapper1);

        QueryWrapper<Products> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("id",pid.trim());
        Products products = productsMapper.selectOne(queryWrapper2);
        BigDecimal unitPrice = products.getPPrice();

        List<Integer> svidList = Arrays.stream(svid.split("[,，]"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        BigDecimal selspecpricetotal = productSpecValuesMapper.selspecpricetotal(svidList);
        unitPrice = unitPrice.add(selspecpricetotal);
        Cartitems cartitems=new Cartitems();
        if(one==null){
//            Cartitems cartitems=new Cartitems();
            cartitems.setCartId(cart.getCartId());
            cartitems.setProductId(Integer.valueOf(pid.trim()));
            cartitems.setQuantity(Integer.valueOf(quantity));
            log.info(svid+"___________"+svid.length());
            cartitems.setSpecvalueId(svid);
            //计算该规格产品单价

            BigDecimal quantityBD = new BigDecimal(quantity);
            quantityBD = quantityBD.multiply(unitPrice);
            cartitems.setPrice(quantityBD);
            cartitems.setCreateTime(LocalDateTime.now());
            cartitems.setUpdateTime(LocalDateTime.now());
            cartitems.setSpecvalueName(svname);
            res=cartitemsMapper.insert(cartitems);
        }
        else {
//            Cartitems cartitems=new Cartitems();
            cartitems.setCartitemId(one.getCartitemId());
            Integer total=Integer.valueOf(quantity)+ one.getQuantity();
            cartitems.setQuantity(total);
            BigDecimal totalPrice =new BigDecimal(String.valueOf(total));
            totalPrice = totalPrice.multiply(unitPrice);
            cartitems.setPrice(totalPrice);
            cartitems.setSpecvalueName(svname);
            res=cartitemsMapper.updateById(cartitems);
        }
        return res;
    }


    public int addCartitemsbynew(String userid,String pid,String quantity,String svid,String svname){
        int res = 0;
        QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        queryWrapper.eq("state","0");
        Cart cart = cartMapper.selectOne(queryWrapper);
        if(cart == null){
            Cart cart1=new Cart();
            cart1.setUserId(Integer.valueOf(userid));
            cart1.setState("0");
            int insert = cartMapper.insert(cart1);
            log.info(insert>0?"插入成功":"插入失败");
        }
        cart = cartMapper.selectOne(queryWrapper);

        QueryWrapper<Cartitems> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("cart_id",cart.getCartId());
        queryWrapper1.eq("product_id",pid.trim());
        queryWrapper1.eq("specvalue_id",svid);
        Cartitems one = cartitemsMapper.selectOne(queryWrapper1);

        QueryWrapper<Products> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("id",pid.trim());
        Products products = productsMapper.selectOne(queryWrapper2);
        BigDecimal unitPrice = products.getPPrice();

        List<Integer> svidList = Arrays.stream(svid.split("[,，]"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        BigDecimal selspecpricetotal = productSpecValuesMapper.selspecpricetotal(svidList);
        unitPrice = unitPrice.add(selspecpricetotal);
        Cartitems cartitems=new Cartitems();
        if(one==null){
//            Cartitems cartitems=new Cartitems();
            cartitems.setCartId(cart.getCartId());
            cartitems.setProductId(Integer.valueOf(pid.trim()));
            cartitems.setQuantity(Integer.valueOf(quantity));
            log.info(svid+"___________"+svid.length());
            cartitems.setSpecvalueId(svid);
            //计算该规格产品单价

            BigDecimal quantityBD = new BigDecimal(quantity);
            quantityBD = quantityBD.multiply(unitPrice);
            cartitems.setPrice(quantityBD);
            cartitems.setCreateTime(LocalDateTime.now());
            cartitems.setUpdateTime(LocalDateTime.now());
            cartitems.setSpecvalueName(svname);
            res=cartitemsMapper.insert(cartitems);
        }
        else {
//            Cartitems cartitems=new Cartitems();
            cartitems.setCartitemId(one.getCartitemId());
            Integer total=Integer.valueOf(quantity)+ one.getQuantity();
            cartitems.setQuantity(total);
            BigDecimal totalPrice =new BigDecimal(String.valueOf(total));
            totalPrice = totalPrice.multiply(unitPrice);
            cartitems.setPrice(totalPrice);
            cartitems.setSpecvalueName(svname);
            res=cartitemsMapper.updateById(cartitems);
        }
        return res;
    }

    public int updateCartitemsbyuidandpid(String cartitemid,String newQuantity){
        Cartitems cartitems = cartitemsMapper.selectById(cartitemid);
        UserCartDetails cartdetalbycartitemid = userCartDetailsMapper.cartdetalbycartitemid(cartitemid);
        BigDecimal unitPrice = cartdetalbycartitemid.getUnitPrice();
        BigDecimal newQuantityBigDecimal = new BigDecimal(Integer.valueOf(newQuantity));
        BigDecimal newPrice = unitPrice.multiply(newQuantityBigDecimal);
        cartitems.setQuantity(Integer.valueOf(newQuantity));
        cartitems.setPrice(newPrice);
        int updateById = cartitemsMapper.updateById(cartitems);
        return updateById;
    }

    @Transactional
    public int delcartitems(List<Integer> cartitemids){
        log.info(cartitemids.toString());
//        String pidStr = pids.stream()
//                .map(String::valueOf)
//                .collect(Collectors.joining(","));
        int delcartitems = cartitemsMapper.delcartitems(cartitemids);
        return delcartitems;
    }

    public int delcartitems2(String cartitemid){
        log.info(cartitemid.toString());
        int delcartitems = cartitemsMapper.delcartitems2(cartitemid);
        return delcartitems;
    }

}
