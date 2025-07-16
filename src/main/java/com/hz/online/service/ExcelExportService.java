package com.hz.online.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.hz.online.entity.OrderExcelDTO;
import com.hz.online.entity.Orders;
import com.hz.online.entity.vo.ExportTaskStatus;
import com.hz.online.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ExcelExportService {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // 线程池控制导出任务
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    /**
     * 启动异步导出任务
     */
    public String exportOrdersAsync() {
        String taskId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        saveTaskStatus2(taskId, "PROCESSING", null, now);

        CompletableFuture.runAsync(() -> {
            File file = null;
            try {
                file = File.createTempFile("orders_" + taskId, ".xlsx");
                file.deleteOnExit();

                // 写入数据
//                EasyExcel.write(file, OrderExcelDTO.class)
//                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                        .sheet("订单数据")
//                        .doWrite(getOrderDataAll());

                ExcelWriter excelWriter = EasyExcel.write(file, OrderExcelDTO.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .build();

                try {
                    int sheetIndex = 1;
                    Long lastId = 0L;
                    int batchSize = 5000;
                    int sheetRowLimit = 800_000; // 每个 sheet 最多写这么多条数据
                    int currentSheetRowCount = 0;

                    WriteSheet writeSheet = EasyExcel.writerSheet("订单数据_" + sheetIndex).build();
                    List<OrderExcelDTO> buffer = new ArrayList<>();

                    boolean hasMore = true;
                    while (hasMore) {
                        List<Orders> orders = ordersService.lambdaQuery()
                                .gt(Orders::getId, lastId)
                                .orderByAsc(Orders::getId)
                                .last("limit " + batchSize)
                                .list();

                        if (orders.isEmpty()) {
                            hasMore = false;
                            break;
                        }

                        for (Orders order : orders) {
                            OrderExcelDTO dto = new OrderExcelDTO();
                            BeanUtils.copyProperties(order, dto);
                            buffer.add(dto);
                            currentSheetRowCount++;

                            if (currentSheetRowCount >= sheetRowLimit) {
                                // 写入当前 sheet
                                excelWriter.write(buffer, writeSheet);
                                // 开始新 sheet
                                sheetIndex++;
                                writeSheet = EasyExcel.writerSheet("订单数据_" + sheetIndex).build();
                                buffer.clear();
                                currentSheetRowCount = 0;
                            }
                        }

                        lastId = orders.stream()
                                .mapToLong(Orders::getId)
                                .max()
                                .orElse(lastId);
                    }

                    // 写最后一部分残余
                    if (!buffer.isEmpty()) {
                        excelWriter.write(buffer, writeSheet);
                    }

                } finally {
                    if (excelWriter != null) {
                        excelWriter.finish();
                    }
                }


                // 上传到 MinIO
                String fileUrl = minioUtil.upload(file,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                saveTaskStatus2(taskId, "COMPLETED", fileUrl, now);

            } catch (Exception e) {
                log.error("导出任务失败", e);
                saveTaskStatus2(taskId, "FAILED", null, now);
            } finally {
                if (file != null && file.exists()) file.delete();
            }
        }, executor);

        return "任务Id："+taskId;
    }


    public String exportOrdersAsync2() {
        //redis 加入时间开销
        String taskId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        // 初始状态：处理中
        saveTaskStatus2(taskId, "PROCESSING", null, now);

        CompletableFuture.runAsync(() -> {
            File file = null;
            ExcelWriter excelWriter = null;

            try {
                file = File.createTempFile("orders_" + taskId.substring(0, 8), ".xlsx");
                file.deleteOnExit();

                excelWriter = EasyExcel.write(file, OrderExcelDTO.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .build();

                int sheetLimit = 200_000;  // 每个sheet最大行数
                int batchSize = 10000;      // 分页大小
                Long lastId = 0L;          // 滚动分页游标
                int sheetIndex = 1;
                boolean hasMore = true;

                List<OrderExcelDTO> currentSheetData = new ArrayList<>();

                while (hasMore) {
                    Long finalLastId = lastId;

                    // 单线程分页，线程池异步查询转换
                    Future<List<OrderExcelDTO>> future = executor.submit(() -> {
                        List<Orders> orders = ordersService.lambdaQuery()
                                .gt(Orders::getId, finalLastId)
                                .orderByAsc(Orders::getId)
                                .last("limit " + batchSize)
                                .list();

                        List<OrderExcelDTO> list = new ArrayList<>();
                        for (Orders order : orders) {
                            OrderExcelDTO dto = new OrderExcelDTO();
                            BeanUtils.copyProperties(order, dto);
                            list.add(dto);
                        }
                        return list;
                    });

                    List<OrderExcelDTO> batch = future.get();

                    if (batch.isEmpty()) {
                        hasMore = false;
                    } else {
                        currentSheetData.addAll(batch);
                        lastId = batch.get(batch.size() - 1).getId();

                        // 满足写入条件，分批写sheet
                        while (currentSheetData.size() >= sheetLimit) {
                            List<OrderExcelDTO> sheetData = new ArrayList<>(currentSheetData.subList(0, sheetLimit));
                            WriteSheet sheet = EasyExcel.writerSheet("订单数据_" + sheetIndex++).build();
                            excelWriter.write(sheetData, sheet);
                            currentSheetData = new ArrayList<>(currentSheetData.subList(sheetLimit, currentSheetData.size()));
                        }
                    }
                }

                // 写入剩余数据
                if (!currentSheetData.isEmpty()) {
                    WriteSheet sheet = EasyExcel.writerSheet("订单数据_" + sheetIndex++).build();
                    excelWriter.write(currentSheetData, sheet);
                }

                // 关闭写入
                excelWriter.finish();

                // 上传 MinIO
                String fileUrl = minioUtil.upload(file,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                // 更新导出完成状态
                saveTaskStatus2(taskId, "COMPLETED", fileUrl, now);

            } catch (Exception e) {
                log.error("导出失败", e);
                saveTaskStatus2(taskId, "FAILED", null, now);
            } finally {
                if (excelWriter != null) {
                    try {
                        excelWriter.finish();
                    } catch (Exception ignored) {
                    }
                }
                if (file != null && file.exists()) {
                    file.delete();
                }
            }
        }, executor);

        return taskId;
    }

    public String exportOrdersAsync3() {
        //redis 加入时间开销
        String taskId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        // 初始状态：处理中
        saveTaskStatus2(taskId, "PROCESSING", null, now);

        CompletableFuture.runAsync(() -> {
            File file = null;
            ExcelWriter excelWriter = null;

            try {
                file = File.createTempFile("orders_" + taskId.substring(0, 8), ".xlsx");
                file.deleteOnExit();

                excelWriter = EasyExcel.write(file, OrderExcelDTO.class)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .build();

                int sheetLimit = 200_000;  // 每个sheet最大行数
                int batchSize = 10000;      // 分页大小
                Long lastId = 0L;          // 滚动分页游标
                int sheetIndex = 1;
                boolean hasMore = true;

                List<OrderExcelDTO> currentSheetData = new ArrayList<>();

                while (hasMore) {
                    Long finalLastId = lastId;

                    // 单线程分页，线程池异步查询转换
                    Future<List<OrderExcelDTO>> future = threadPoolTaskExecutor.submit(() -> {
                        List<Orders> orders = ordersService.lambdaQuery()
                                .gt(Orders::getId, finalLastId)
                                .orderByAsc(Orders::getId)
                                .last("limit " + batchSize)
                                .list();

                        List<OrderExcelDTO> list = new ArrayList<>();
                        for (Orders order : orders) {
                            OrderExcelDTO dto = new OrderExcelDTO();
                            BeanUtils.copyProperties(order, dto);
                            list.add(dto);
                        }
                        return list;
                    });

                    List<OrderExcelDTO> batch = future.get();

                    if (batch.isEmpty()) {
                        hasMore = false;
                    } else {
                        currentSheetData.addAll(batch);
                        lastId = batch.get(batch.size() - 1).getId();

                        // 满足写入条件，分批写sheet
                        while (currentSheetData.size() >= sheetLimit) {
                            List<OrderExcelDTO> sheetData = new ArrayList<>(currentSheetData.subList(0, sheetLimit));
                            WriteSheet sheet = EasyExcel.writerSheet("订单数据_" + sheetIndex++).build();
                            excelWriter.write(sheetData, sheet);
                            currentSheetData = new ArrayList<>(currentSheetData.subList(sheetLimit, currentSheetData.size()));
                        }
                    }
                }

                // 写入剩余数据
                if (!currentSheetData.isEmpty()) {
                    WriteSheet sheet = EasyExcel.writerSheet("订单数据_" + sheetIndex++).build();
                    excelWriter.write(currentSheetData, sheet);
                }

                // 关闭写入
                excelWriter.finish();

                // 上传 MinIO
                String fileUrl = minioUtil.upload(file,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                // 更新导出完成状态
                saveTaskStatus2(taskId, "COMPLETED", fileUrl, now);

            } catch (Exception e) {
                log.error("导出失败", e);
                saveTaskStatus2(taskId, "FAILED", null, now);
            } finally {
                if (excelWriter != null) {
                    try {
                        excelWriter.finish();
                    } catch (Exception ignored) {
                    }
                }
                if (file != null && file.exists()) {
                    file.delete();
                }
            }
        }, threadPoolTaskExecutor);

        return taskId;
    }





    /**
     * 查询导出任务状态
     */
    public ExportTaskStatus getTaskStatus(String taskId) {
        return (ExportTaskStatus) redisTemplate.opsForValue().get("export:task:" + taskId);
    }

    /**
     * 存储导出任务状态到 Redis
     */
    private void saveTaskStatus(String taskId, String status, String fileUrl) {
        ExportTaskStatus taskStatus = new ExportTaskStatus();
        taskStatus.setTaskId(taskId);
        taskStatus.setStatus(status);
        taskStatus.setFileUrl(fileUrl);
        taskStatus.setUpdateTime(LocalDateTime.now());
        redisTemplate.opsForValue().set("export:task:" + taskId, taskStatus, 1, TimeUnit.HOURS);
    }

    private void saveTaskStatus2(String taskId, String status, String fileUrl, LocalDateTime createTime) {
        ExportTaskStatus statusObj = new ExportTaskStatus();
        statusObj.setTaskId(taskId);
        statusObj.setStatus(status);
        statusObj.setFileUrl(fileUrl);
        statusObj.setCreateTime(createTime);
        LocalDateTime updateTime = LocalDateTime.now();
        statusObj.setUpdateTime(updateTime);
        if (createTime != null && updateTime != null) {
            long seconds = Duration.between(createTime, updateTime).getSeconds();
            statusObj.setDurationSeconds(seconds);
        }


        redisTemplate.opsForValue().set("export:task:" + taskId, statusObj, 1, TimeUnit.HOURS);
    }


    /**
     * 使用 Stream 方式分页拉取数据并写入
     */
    private Stream<OrderExcelDTO> getOrderDataStream() {
        Iterator<OrderExcelDTO> iterator = new Iterator<OrderExcelDTO>() {
            private final int batchSize = 5000;
            private Long lastId = 0L;
            private Queue<OrderExcelDTO> buffer = new LinkedList<>();
            private boolean end = false;

            @Override
            public boolean hasNext() {
                if (!buffer.isEmpty()) return true;
                if (end) return false;

                List<Orders> orders = ordersService.lambdaQuery()
                        .gt(Orders::getId, lastId)
                        .orderByAsc(Orders::getId)
                        .last("limit " + batchSize)
                        .list();

                if (orders.isEmpty()) {
                    end = true;
                    return false;
                }

                for (Orders order : orders) {
                    OrderExcelDTO dto = new OrderExcelDTO();
                    BeanUtils.copyProperties(order, dto);
                    buffer.add(dto);
                }

                lastId = orders.stream()
                        .mapToLong(Orders::getId)
                        .max()
                        .orElse(lastId);

                return !buffer.isEmpty();
            }

            @Override
            public OrderExcelDTO next() {
                return buffer.poll();
            }
        };

        // 将 Iterator 转换为非并行的 Stream
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }

    /**
     * 分批加载所有订单数据，返回集合（一次性收集）
     */
    private List<OrderExcelDTO> getOrderDataAll() {
        List<OrderExcelDTO> result = new ArrayList<>();
        Long lastId = 0L;
        int batchSize = 5000;
        boolean hasMore = true;

        while (hasMore) {
            List<Orders> orders = ordersService.lambdaQuery()
                    .gt(Orders::getId, lastId)
                    .orderByAsc(Orders::getId)
                    .last("limit " + batchSize)
                    .list();

            if (orders.isEmpty()) {
                hasMore = false;
                break;
            }

            for (Orders order : orders) {
                OrderExcelDTO dto = new OrderExcelDTO();
                BeanUtils.copyProperties(order, dto);
                result.add(dto);
            }

            lastId = orders.stream()
                    .mapToLong(Orders::getId)
                    .max()
                    .orElse(lastId);
        }

        return result;
    }



}
