package com.hz.online.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/yunguoonline?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","root")
                .globalConfig(builder->{
                    builder.author("haozi").fileOverride().outputDir("D:\\ideaWork\\yunguoOnline\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.hz.online").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\ideaWork\\yunguoOnline\\src\\main\\java\\com\\hz\\online\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("cron_task_info22222");
                })
                .templateEngine(new FreemarkerTemplateEngine())//模板
                .execute();
    }
}
