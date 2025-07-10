package com.hz.online.controller;

import com.hz.online.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            String upload = minioUtil.upload(file);
            log.info("上传成功(地址): " + upload);
            return upload;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("objectName") String objectName) {
        try {
            minioUtil.delete(objectName);
            log.info("删除成功: {}", objectName);
            return "删除成功: " + objectName;
        } catch (Exception e) {
            log.error("删除失败", e);
            return "删除失败: " + e.getMessage();
        }
    }

    @DeleteMapping("/deleteByUrl")
    public String deleteByUrl(@RequestParam("fileUrl") String fileUrl) {
        try {
            String objectName = minioUtil.getObjectNameFromUrl(fileUrl);
            minioUtil.delete(objectName);
            log.info("根据 URL 删除成功: {}", objectName);
            return "删除成功: " + objectName;
        } catch (Exception e) {
            log.error("删除失败", e);
            return "删除失败: " + e.getMessage();
        }
    }


}
