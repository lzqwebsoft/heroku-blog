package com.herokuapp.lzqwebsoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.service.ImageService;
import com.herokuapp.lzqwebsoft.util.QiniuUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * 工具控制器，用于提供一些工具操作
 *
 * @author ZQLUO
 */
@Controller
@RequestMapping("/tools")
public class ToolsController extends BaseController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ArticleService articleService;

    // 七牛云图片文件批量上传备份工具
    @ResponseBody
    @RequestMapping(value = "/images_upload.html")
    public List<Map> imageUpload(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        List<Map> list = new ArrayList<Map>();

        List<Image> images = imageService.getAllImages();
        if (images != null && images.size() > 0) {
            for (Image image : images) {
                Map<String, String> map = new HashMap<String, String>();
                String status = "不需要上传";
                if (image.getQiniuKey() == null || image.getQiniuKey().trim().length() == 0) {
                    image = QiniuUtil.upload(image);  // 七牛云上传图片
                    if (image.getQiniuKey() != null && image.getQiniuKey().trim().length() > 0) {
                        imageService.update(image);
                        status = "上传成功";
                    } else {
                        status = "上传失败";
                    }
                }
                map.put("id", image.getId());
                map.put("filename", image.getFileName());
                map.put("qiniuKey", image.getQiniuKey());
                map.put("status", status);
                list.add(map);
            }
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/replace_image_urls.html")
    public String replaceImageUrls(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        List<Article> articles = articleService.getTotalArticles();
        if (articles != null && articles.size() > 0) {
            for(Article article : articles) {
                String content = article.getContent();
                content = content.replaceAll("(\\/images\\/show\\/\\d{14}\\w{30}).html", "$1.jpg");
                article.setContent(content);
                articleService.update(article);
            }
        }
        return successJSON("替换后缀HTML图片到JPG后缀成功！");
    }
}
