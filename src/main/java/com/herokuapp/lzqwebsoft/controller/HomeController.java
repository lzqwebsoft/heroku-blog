package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.BlogInfo;
import com.herokuapp.lzqwebsoft.pojo.ChangePasswordUserBean;
import com.herokuapp.lzqwebsoft.pojo.Page;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.service.BlogInfoService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.MakeCertPic;

@Controller
public class HomeController{
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articleTypeService;
    
    @RequestMapping(value="/index")
    public String home(ModelMap model, String pageNo) {
        if(pageNo==null||pageNo.trim().length()<=0)
            pageNo = "1";
        int pageNoIndex = Integer.parseInt(pageNo);
        Page<Article> page = articleService.getAllAricle(pageNoIndex, 15);
        // 当请求的页面数中没有数据，则重定向到上一页
        if(pageNoIndex>1&&page.getData().size()<=0) {
            model.addAttribute("pageNo", pageNoIndex-1);
            return "redirect:/index.html";
        }
        model.addAttribute("page", page);
        
        // 阅读排行榜的前10篇博文
        List<Article> top10Articles = articleService.getReadedTop10();
        model.addAttribute("top10Articles", top10Articles);
        
        List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
        model.addAttribute("articleTypes", articleTypes);
        return "index";
    }
    
    @RequestMapping(value="/select/{articleTypeId}")
    public String select(@PathVariable("articleTypeId")int articleTypeId,
            String pageNo, ModelMap model) {
        if(pageNo==null||pageNo.trim().length()<=0)
            pageNo = "1";
        int pageNoIndex = Integer.parseInt(pageNo);
        
        Page<Article> articles = articleService.getArticleByTypeId(articleTypeId, pageNoIndex, 15);
        // 当请求的页面数中没有数据，则重定向到上一页
        if(pageNoIndex>1&&articles.getData().size()<=0) {
            model.addAttribute("pageNo", pageNoIndex-1);
            return "redirect:/select/"+articleTypeId+".html";
        }
        model.addAttribute("page", articles);
        
        List<Article> top10Articles = articleService.getReadedTop10();
        model.addAttribute("top10Articles", top10Articles);
        
        List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
        model.addAttribute("articleTypes", articleTypes);
        return "index";
    }
    
    @RequestMapping(value="/change_password")
    public String changePassword(ModelMap model){
        model.addAttribute("userBean", new ChangePasswordUserBean());
        return "change_password";
    }
    
    @RequestMapping(value="/about")
    public void about(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            BlogInfo blogInfo = blogInfoService.getSystemBlogInfo();
            
            out.print("<div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button><h4 class=\"modal-title\" id=\"myModalLabel\">关于站点</h4></div><div class=\"modal-body\">");
            out.print(blogInfo.getAbout());
            out.print("</div><div class=\"modal-footer\"><button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">确定</button></div></div></div>");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            if(out!=null) {
                out.close();
            }
        }
    }
    
    /**
     * 图片验证码
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "No-cache");  
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        
        try {
            MakeCertPic certPic = new MakeCertPic();
            String certString = certPic.getCertPic(100, 30, response.getOutputStream());
            //设置session对象5分钟失效
//            session.setMaxInactiveInterval(5*60);
            request.getSession().setAttribute(CommonConstant.CAPTCHA, certString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/getHeaders")
    public Map<String, String>  headers(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
    
    @RequestMapping(value="/error404")
    public String error404() {
        return "error404";
    }
    
    @RequestMapping(value="/error503")
    public String error503() {
        return "error503";
    }
}