package com.hz.online.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.online.common.constant.AllConstant;
import com.hz.online.common.dto.ResponseResult;
import com.hz.online.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hz.online.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author haozi
 * @since 2024-06-20
 */
@Service
@Slf4j
public class ArticleService  {

    @Autowired
    ArticleMapper articleMapper;

    public ResponseResult<List<Article>> allArticle(String artcateid){
        QueryWrapper<Article> queryWrapper=new QueryWrapper();
        queryWrapper.eq("article_category", artcateid);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return ResponseResult.success(articles);
    }

    public ResponseResult<List<Article>> allArticlebyarticletitle(String articletitle){
        QueryWrapper<Article> queryWrapper=new QueryWrapper();
        queryWrapper.eq("article_category", AllConstant.Fruit);
        queryWrapper.like("article_title", articletitle);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return ResponseResult.success(articles);
    }

    public ResponseResult<List<Article>> allArticlebyarticletitle2(String articletitle){
        QueryWrapper<Article> queryWrapper=new QueryWrapper();
        queryWrapper.like("article_title", articletitle);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return ResponseResult.success(articles);
    }
}
