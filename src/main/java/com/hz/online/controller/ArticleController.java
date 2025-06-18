package com.hz.online.controller;


import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Article;
import com.hz.online.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author haozi
 * @since 2024-06-20
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping("/allArticle")
    public ResponseResult<List<Article>> allArticle(@RequestParam String artcateid){
        return articleService.allArticle(artcateid);
    }

    @PostMapping("/allArticle2")
    public ResponseResult<List<Article>> allArticle2(@RequestParam String articletitle){
        return articleService.allArticlebyarticletitle(articletitle);
    }

    @PostMapping("/allArticle3")
    public ResponseResult<List<Article>> allArticle3(@RequestParam String articletitle){
        return articleService.allArticlebyarticletitle2(articletitle);
    }
}
