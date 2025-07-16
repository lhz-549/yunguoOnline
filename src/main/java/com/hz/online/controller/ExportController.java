package com.hz.online.controller;

import com.hz.online.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
public class ExportController {
    @Autowired
    ExcelExportService excelExportService;

    @RequestMapping("/export")
    public String exportOrdersAsync() {
        return excelExportService.exportOrdersAsync3();
    }
}
