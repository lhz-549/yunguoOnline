package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.*;
import com.hz.online.mapper.*;
import com.hz.online.utils.SumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单项表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-19
 */
@Service
@Slf4j
public class OrderitemsService  {

    @Autowired
    private CronTaskInfoMapper cronTaskInfoMapper;

    @Autowired
    OrderitemsMapper orderitemsMapper;

    private static OrderitemsMapper orderitemsMapper2;

    @Autowired
    public void setOrderitemsMapper(OrderitemsMapper orderitemsMapper) {
        OrderitemsService.orderitemsMapper2 = orderitemsMapper;
    }

    @Autowired
    ProductsMapper productsMapper;

    private static ProductsMapper productsMapper2;

    @Autowired
    public void setProductsMapper(ProductsMapper productsMapper) {
        OrderitemsService.productsMapper2 = productsMapper;
    }

    @Autowired
    ProductSpecValuesMapper productSpecValuesMapper;

    private static ProductSpecValuesMapper productSpecValuesMapper2;

    @Autowired
    public void setProductSpecValuesMapper(ProductSpecValuesMapper productSpecValuesMapper) {
        OrderitemsService.productSpecValuesMapper2 = productSpecValuesMapper;
    }

    @Autowired
    PaymentsMapper paymentsMapper;

    private static PaymentsMapper paymentsMapper2;

    @Autowired
    public void setPaymentsMapper(PaymentsMapper paymentsMapper) {
        OrderitemsService.paymentsMapper2 = paymentsMapper;
    }

    @Autowired
    YuebalanceMapper yuebalanceMapper;

    private static YuebalanceMapper yuebalanceMapper2;

    @Autowired
    public void setYuebalanceMapper(YuebalanceMapper yuebalanceMapper) {
        OrderitemsService.yuebalanceMapper2 = yuebalanceMapper;
    }

    @Autowired
    YuechangeMapper yuechangeMapper;

    private static YuechangeMapper yuechangeMapper2;

    @Autowired
    public void setYuechangeMapper(YuechangeMapper yuechangeMapper) {
        OrderitemsService.yuechangeMapper2 = yuechangeMapper;
    }

    @Autowired
    CartitemsMapper cartitemsMapper;

    public ResponseResult<List<OrderItemDto>> allorderByuserid(String userid){
        List<OrderItemDto> orderItemDtos = orderitemsMapper.allorderByuserid(userid);
        Map<String, Object> totle = SumUtils.calculateSums(orderItemDtos,
                Arrays.asList("price","quantity","selledQuantity","stockQuantity"));
//        BigDecimal totalAmount = orderItemDtos.stream()
//                .map(OrderItemDto::getPrice)
//                .filter(Objects::nonNull)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> map = new HashMap<>();
        map.put("orderItemDtos",orderItemDtos);
        map.put("totle",totle);
        return ResponseResult.success(map);
//        return ResponseResult.success(orderItemDtos);
    }

    public ResponseResult<List<OrderItemDto>> allorderByuseridandcondition(String userid,String pname){
        List<OrderItemDto> orderItemDtos = orderitemsMapper.allorderByuseridandcondition(userid,pname);
        if(orderItemDtos.isEmpty())
            return ResponseResult.fail();
        else
            return ResponseResult.success(orderItemDtos);
    }

    @Transactional
    public ResponseResult addorder(AddOrderRequestDTO addOrderRequestDTO){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String formattedDate = now.format(formatter)+addOrderRequestDTO.getUserid()+generateDigitRandomNumber();

        Orderitems orderitems =new Orderitems();
//        orderitems.setOrderitemId();
        orderitems.setUserId(Integer.valueOf(addOrderRequestDTO.getUserid()));

        orderitems.setOrderNum(formattedDate);
        orderitems.setProductId(Integer.valueOf(addOrderRequestDTO.getProductId()));
        orderitems.setQuantity(Integer.valueOf(addOrderRequestDTO.getQuantity()));
        //计算价格
        QueryWrapper<Products> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("id",addOrderRequestDTO.getProductId().trim());
        Products products = productsMapper.selectOne(queryWrapper2);
        BigDecimal unitPrice = products.getPPrice();
        List<Integer> svidList = Arrays.stream(addOrderRequestDTO.getSpecvalueId().split("[,，]"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        BigDecimal selspecpricetotal = productSpecValuesMapper.selspecpricetotal(svidList);
        unitPrice = unitPrice.add(selspecpricetotal);
        BigDecimal quantityBD = new BigDecimal(addOrderRequestDTO.getQuantity());
        quantityBD = quantityBD.multiply(unitPrice);

        orderitems.setPrice(quantityBD);
        orderitems.setOrderitemState("1");
        orderitems.setUpdateTime(LocalDateTime.now());
        orderitems.setFinishTime(LocalDateTime.now());
        orderitems.setSpecvalueId(addOrderRequestDTO.getSpecvalueId());
        orderitems.setSpecvalueName(addOrderRequestDTO.getSpecvalueName());
        orderitems.setOriginalOrdernum(null);
        orderitems.setUsedNum(Integer.valueOf(addOrderRequestDTO.getQuantity()));
        orderitems.setRemark(addOrderRequestDTO.getRemark());
        int insert = orderitemsMapper.insert(orderitems);
        if(insert>0){
            CronTaskInfo cronTaskInfo = new CronTaskInfo();
            LocalDateTime futureTime = now.plusMinutes(10);
            String cronExpression = generateCronExpression(futureTime);
            //打印cron表达式
            log.info("Cron Expression: " + cronExpression);
            cronTaskInfo.setCronExpression(cronExpression);
            cronTaskInfo.setTaskData(String.valueOf(orderitems.getOrderitemId()));
            cronTaskInfo.setTaskDesc("10minlaterAutoCancelOrder");
            cronTaskInfoMapper.insert(cronTaskInfo);
            return ResponseResult.success(orderitems.getOrderitemId());
        } else
            return ResponseResult.fail();
    }

    private static String generateCronExpression(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss mm HH dd MM ? yyyy");
        return formatter.format(dateTime);
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        // 格式化日期时间
        String formattedDate = now.format(formatter);
        System.out.println(formattedDate);
        int randomNumber = generateDigitRandomNumber();
        System.out.println("Generated Five-Digit Random Number: " + randomNumber);
    }
    public static int generateDigitRandomNumber() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        return randomNumber;
    }

    public ResponseResult<List<FindPlantReqDTO>> findusedplant(String userid){
        List<FindPlantReqDTO> findusedplant = orderitemsMapper.findusedplant(userid);
        return ResponseResult.success(findusedplant);
    }

    @Transactional
    public ResponseResult updateorderstatus(String orderid, String orderstate, String paystate, String paymethod){
        Orderitems orderitem = orderitemsMapper.selectById(orderid);
        if(orderitem!=null){
            BigDecimal price = orderitem.getPrice();
            QueryWrapper<Yuebalance> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_id",orderitem.getUserId());
            Yuebalance yuebalance = yuebalanceMapper.selectOne(queryWrapper);
            //先要判断该用户余额表是否存在
            if(yuebalance==null){
                return ResponseResult.fail("您还未开通云果余额账号");
            }
            BigDecimal yueAmount = yuebalance.getYueAmount();
            if(yueAmount.compareTo(price) >= 0){
                yuebalance.setYueAmount(yueAmount.subtract(price));
                yuebalanceMapper.updateById(yuebalance);

                QueryWrapper<CronTaskInfo> queryWrapper2=new QueryWrapper<>();
                queryWrapper2.eq("task_desc","10minlaterAutoCancelOrder");
                queryWrapper2.eq("task_data",orderitem.getOrderitemId());
                CronTaskInfo cronTaskInfo1 = cronTaskInfoMapper.selectOne(queryWrapper2);
                if(cronTaskInfo1!=null){
                    cronTaskInfoMapper.deleteById(cronTaskInfo1.getId());
                }

                Yuechange yuechange = new Yuechange();
                yuechange.setUserId(orderitem.getUserId());
                yuechange.setYueId(yuebalance.getYueId());
                yuechange.setYueBefore(yueAmount);
                yuechange.setYueChange("-"+price);
                yuechange.setYueAfter(yueAmount.subtract(price));
                yuechange.setChangeTime(LocalDateTime.now());
                String str = "";
                if(price.compareTo(BigDecimal.ZERO) < 0){
                    str = "——退款";
                }else if(price.compareTo(BigDecimal.ZERO) > 0){
                    str = "——付款";
                }
                yuechange.setYueRemark("系统自动生成"+str);
                yuechangeMapper.insert(yuechange);

                orderitem.setOrderitemState(orderstate);
                int updateById = orderitemsMapper.updateById(orderitem);
                Payments payments=new Payments();
                payments.setOrderId(Integer.valueOf(orderid));
                String s = "";
                if(paymethod.equals("2"))
                    s = "云果余额支付";
                else{
                    s = "微信支付";
                }
                payments.setPayMethod(s);
                payments.setPayStatus(paystate);
                payments.setPayAmount(price);
                payments.setPayDate(LocalDateTime.now());
                int insert = paymentsMapper.insert(payments);
                insert = updateById>insert? insert:updateById;
                return ResponseResult.success(insert);
            }else{
                return ResponseResult.fail("余额不足");
            }
        }
        return ResponseResult.fail("检查入参");
    }
    public ResponseResult<OrderItemDto> selectorderinfobyorderid(String orderid){
        OrderItemDto selectorderinfobyorderid = orderitemsMapper.selectorderinfobyorderid(orderid);
        return ResponseResult.success(selectorderinfobyorderid);
    }

    @Transactional
    public ResponseResult updateorderstate( String orderid,  String state){
        Orderitems orderitem = orderitemsMapper.selectById(orderid);
        if(orderitem!=null){
            orderitem.setOrderitemState(state);
            int updateById = orderitemsMapper.updateById(orderitem);
            if(updateById>0){
                QueryWrapper<CronTaskInfo> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("task_desc","10minlaterAutoCancelOrder");
                queryWrapper.eq("task_data",orderitem.getOrderitemId());
                CronTaskInfo cronTaskInfo1 = cronTaskInfoMapper.selectOne(queryWrapper);
                if(cronTaskInfo1!=null){
                    cronTaskInfoMapper.deleteById(cronTaskInfo1.getId());
                }
            }
            return ResponseResult.success(updateById);
        }
        return ResponseResult.fail("请核查入参");
    }

    @Transactional
    public ResponseResult updateorderstrstate( String orderitemidstr,  String state){
        String[] orderitemidstrArray = orderitemidstr.split("[,，]");
        int updateById = 1;
        for (String orderid: orderitemidstrArray) {
            Orderitems orderitem = orderitemsMapper.selectById(orderid);
            if(orderitem!=null){
                orderitem.setOrderitemState(state);
                int updateById2 = orderitemsMapper.updateById(orderitem);

                QueryWrapper<CronTaskInfo> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("task_desc","10minlaterAutoCancelOrder");
                queryWrapper.eq("task_data",orderitem.getOrderitemId());
                CronTaskInfo cronTaskInfo1 = cronTaskInfoMapper.selectOne(queryWrapper);
                if(cronTaskInfo1!=null){
                    cronTaskInfoMapper.deleteById(cronTaskInfo1.getId());
                }

                updateById = updateById>updateById2?updateById2:updateById;
            }
        }
        return ResponseResult.success(updateById);
    }

    @Transactional
    public ResponseResult deleteorderbyid( String orderid){
        int deleteById = orderitemsMapper.deleteById(orderid);
        if(deleteById>0){
            QueryWrapper<CronTaskInfo> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("task_desc","10minlaterAutoCancelOrder");
            queryWrapper.eq("task_data",orderid);
            CronTaskInfo cronTaskInfo1 = cronTaskInfoMapper.selectOne(queryWrapper);
            if(cronTaskInfo1!=null){
                cronTaskInfoMapper.deleteById(cronTaskInfo1.getId());
            }
        }
        return ResponseResult.success(deleteById);
    }

    @Transactional
    public ResponseResult deleteorderbyidstr( String orderitemidstr){
        String[] orderitemidstrArray = orderitemidstr.split("[,，]");
        int res= 1;
        for (String orderid: orderitemidstrArray) {
            int res2 = orderitemsMapper.deleteById(orderid);
            if(res2>0){
                QueryWrapper<CronTaskInfo> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("task_desc","10minlaterAutoCancelOrder");
                queryWrapper.eq("task_data",orderid);
                CronTaskInfo cronTaskInfo1 = cronTaskInfoMapper.selectOne(queryWrapper);
                if(cronTaskInfo1!=null){
                    cronTaskInfoMapper.deleteById(cronTaskInfo1.getId());
                }
            }
            res = res>res2?res2:res;
        }
        return ResponseResult.success(res);
    }


    @Transactional
    public ResponseResult updateorderstatus2(String userid, String orderitemidstr, String orderstate, String paystate, String paymethod){
        try {
            String[] orderitemidstrArray = orderitemidstr.split("[,，]");
            List<Integer> orderitemidstrA = Arrays.stream(orderitemidstrArray)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            int total = orderitemsMapper.selectallpricebyidstr(orderitemidstrA);
            QueryWrapper<Yuebalance> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_id",userid);
            Yuebalance yuebalance = yuebalanceMapper.selectOne(queryWrapper);
            BigDecimal yueAmount = yuebalance.getYueAmount();

            int zuhepay = 1;
            if(yueAmount.compareTo(BigDecimal.valueOf(total)) >= 0){
                for (String orderid : orderitemidstrArray) {
                    int res = zuhepay(orderid, orderstate, paystate, paymethod);
                    if(res>0){
                        QueryWrapper<CronTaskInfo> queryWrapper2=new QueryWrapper<>();
                        queryWrapper2.eq("task_desc","10minlaterAutoCancelOrder");
                        queryWrapper2.eq("task_data",orderid);
                        CronTaskInfo cronTaskInfo1 = cronTaskInfoMapper.selectOne(queryWrapper2);
                        if(cronTaskInfo1!=null){
                            cronTaskInfoMapper.deleteById(cronTaskInfo1.getId());
                        }
                    }
                    zuhepay = zuhepay>res?res:zuhepay;
                }
                return ResponseResult.success(zuhepay);
            }else {
                return ResponseResult.fail("余额不足");
            }
        } catch (Exception e) {
            // 日志记录错误信息
            e.printStackTrace();
            return ResponseResult.fail("处理失败: " + e.getMessage());
        }
    }

    public static int zuhepay(String orderid, String orderstate, String paystate, String paymethod){
        try {
            Orderitems orderitem = orderitemsMapper2.selectById(orderid);
            BigDecimal price = orderitem.getPrice();
            QueryWrapper<Yuebalance> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_id",orderitem.getUserId());
            Yuebalance yuebalance = yuebalanceMapper2.selectOne(queryWrapper);
            BigDecimal yueAmount = yuebalance.getYueAmount();

            if(yueAmount.compareTo(price) >= 0){
                yuebalance.setYueAmount(yueAmount.subtract(price));
                yuebalanceMapper2.updateById(yuebalance);

                Yuechange yuechange = new Yuechange();
                yuechange.setUserId(orderitem.getUserId());
                yuechange.setYueId(yuebalance.getYueId());
                yuechange.setYueBefore(yueAmount);
                yuechange.setYueChange("-"+price);
                yuechange.setYueAfter(yueAmount.subtract(price));
                yuechange.setChangeTime(LocalDateTime.now());
                String str = "";
                if(price.compareTo(BigDecimal.ZERO) < 0){
                    str = "——退款";
                }else if(price.compareTo(BigDecimal.ZERO) > 0){
                    str = "——付款";
                }
                yuechange.setYueRemark("系统自动生成"+str);
                yuechangeMapper2.insert(yuechange);

                orderitem.setOrderitemState(orderstate);
                orderitem.setUpdateTime(LocalDateTime.now());
                int updateById = orderitemsMapper2.updateById(orderitem);
                Payments payments=new Payments();
                payments.setOrderId(Integer.valueOf(orderid));
                String s = "";
                if(paymethod.equals("2"))
                    s = "云果余额支付";
                else{
                    s = "微信支付";
                }
                payments.setPayMethod(s);
                payments.setPayStatus(paystate);
                payments.setPayAmount(price);
                payments.setPayDate(LocalDateTime.now());
                int insert = paymentsMapper2.insert(payments);
                insert = updateById>insert? insert:updateById;
                return insert;
            }else{
                return -1;
            }
        } catch (Exception e) {
            // 日志记录错误信息
            e.printStackTrace();
            return -1;
        }
    }

    public ResponseResult addorderzuhepay(String userid, String cartitemidstr){
        String[] cartitemidstrArray = cartitemidstr.split("[,，]");
        List<Integer> cartitemidstrA = Arrays.stream(cartitemidstrArray)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        List<Cartitems> cartitemsbystrs = cartitemsMapper.selcartitemsbystrs(cartitemidstrA);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String formattedDate = now.format(formatter)+userid+generateDigitRandomNumber();
        int insert = 1;
        List orderlist = new ArrayList();
        for (Cartitems cartitem:cartitemsbystrs) {
            Orderitems orderitems=new Orderitems();

            orderitems.setUserId(Integer.valueOf(userid));
            //订单号
            orderitems.setOrderNum(formattedDate);
            orderitems.setProductId(cartitem.getProductId());
            orderitems.setQuantity(cartitem.getQuantity());
            orderitems.setPrice(cartitem.getPrice());
            orderitems.setOrderitemState("1");
            orderitems.setUpdateTime(now);
            //创建时间
            orderitems.setFinishTime(now);
            orderitems.setSpecvalueId(cartitem.getSpecvalueId());
            orderitems.setSpecvalueName(cartitem.getSpecvalueName());
            orderitems.setOriginalOrdernum(null);
            orderitems.setUsedNum(cartitem.getQuantity());
            orderitems.setRemark("测试组合pay");
            int insert2 = orderitemsMapper.insert(orderitems);
            cartitemsMapper.deleteById(cartitem.getCartitemId());
            orderitems.getOrderitemId();
            orderlist.add(orderitems.getOrderitemId());
            insert = insert > insert2 ? insert2 : insert;
            log.info(orderlist.toString());
        }
        String result = (String) orderlist.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Map map=new HashMap();
        map.put("ordernum",formattedDate);
        map.put("orderstr",result);
        map.put("ordercreatetime",now);
        //log.info(map.toString());
        if (insert>0)
            return ResponseResult.success(map);
        else
            return ResponseResult.fail();
    }

    @Transactional
    public ResponseResult addorderzuhepay2(String userid, String cartitemidstr,String amount){
        String[] cartitemidstrArray = cartitemidstr.split("[,，]");
        List<Integer> cartitemidstrA = Arrays.stream(cartitemidstrArray)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        List<Cartitems> cartitemsbystrs = cartitemsMapper.selcartitemsbystrs(cartitemidstrA);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String formattedDate = now.format(formatter)+userid+generateDigitRandomNumber();
        int insert = 1;
        List orderlist = new ArrayList();
        for (Cartitems cartitem:cartitemsbystrs) {
            Orderitems orderitems=new Orderitems();

            orderitems.setUserId(Integer.valueOf(userid));
            //订单号
            orderitems.setOrderNum(formattedDate);
            orderitems.setProductId(cartitem.getProductId());
            orderitems.setQuantity(cartitem.getQuantity());
            orderitems.setPrice(cartitem.getPrice());
            orderitems.setOrderitemState("1");
            orderitems.setUpdateTime(now);
            //创建时间
            orderitems.setFinishTime(now);
            orderitems.setSpecvalueId(cartitem.getSpecvalueId());
            orderitems.setSpecvalueName(cartitem.getSpecvalueName());
            orderitems.setOriginalOrdernum(null);
            orderitems.setUsedNum(cartitem.getQuantity());
            orderitems.setRemark(amount);
            int insert2 = orderitemsMapper.insert(orderitems);

            CronTaskInfo cronTaskInfo = new CronTaskInfo();
            LocalDateTime futureTime = now.plusMinutes(10);
            String cronExpression = generateCronExpression(futureTime);
            //打印cron表达式
            log.info("Cron Expression: " + cronExpression);
            cronTaskInfo.setCronExpression(cronExpression);
            cronTaskInfo.setTaskData(String.valueOf(orderitems.getOrderitemId()));
            cronTaskInfo.setTaskDesc("10minlaterAutoCancelOrder");
            cronTaskInfoMapper.insert(cronTaskInfo);

            cartitemsMapper.deleteById(cartitem.getCartitemId());
            orderitems.getOrderitemId();
            orderlist.add(orderitems.getOrderitemId());
            insert = insert > insert2 ? insert2 : insert;
            log.info(orderlist.toString());
        }
        String result = (String) orderlist.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Map map=new HashMap();
        map.put("ordernum",formattedDate);
        map.put("orderstr",result);
        map.put("ordercreatetime",now);
        //log.info(map.toString());
        if (insert>0)
            return ResponseResult.success(map);
        else
            return ResponseResult.fail();
    }


    public ResponseResult<List<OrderItemDto>> selectorderinfobyorderitemidstr(String orderitemidstr){
        String[] orderitemidstrArray = orderitemidstr.split("[,，]");
        List<Integer> orderitemidstrA = Arrays.stream(orderitemidstrArray)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        List<OrderItemDto> itemDtos = orderitemsMapper.selectorderinfobyorderitemidstr(orderitemidstrA);
        return  ResponseResult.success(itemDtos);
    }

    public ResponseResult<List<RGadoptDTO>> selUsedRGAdoptNum( String userid){
        if(userid.isEmpty()||userid.equals("")){
            return ResponseResult.fail("请传入有效的userid");
        }
        List<RGadoptDTO> rGadoptDTOS = orderitemsMapper.selUsedRGAdoptNum(userid);
        return ResponseResult.success(rGadoptDTOS);
    }

}
