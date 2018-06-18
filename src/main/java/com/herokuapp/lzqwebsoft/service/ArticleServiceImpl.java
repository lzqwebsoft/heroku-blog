package com.herokuapp.lzqwebsoft.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.ArticleDAO;
import com.herokuapp.lzqwebsoft.dao.ArticleTypeDAO;
import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.Page;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleTypeDAO articleTypeDAO;

    @Autowired
    private ArticleDAO articleDAO;

    public void save(Article article, String typeName, boolean modelNew, boolean isDraft) {
        // 判断文章的类型是否通过文本框输入来得到
        if (modelNew) {
            ArticleType type = articleTypeDAO.getArticleTypeByName(typeName);
            if (type == null) {
                type = new ArticleType();
                type.setName(typeName);
                type.setDisable(false); // 生成文章内容的路径，关保存为文本文件
                type.setCreateAt(new Date());
                type.setUpdateAt(new Date());
                articleTypeDAO.save(type);
                article.setType(type);
            } else {
                article.setType(type);
            }
        } else {
            ArticleType type = articleTypeDAO.getArticleTypeById(article.getType().getId());
            article.setType(type);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String id = format.format(new Date());

        // 文件对象入库
        Date now = new Date();
        article.setId(id);
        // 判断是否将文章保存为草稿或发表
        if (isDraft)
            article.setStatus(0);
        else
            article.setStatus(1);
        article.setReadedNum(0);
        article.setCreateAt(now);
        article.setUpdateAt(now);
        articleDAO.save(article);
    }

    public void update(Article article, String typeName, boolean modelNew, boolean isDraft) {
        // 判断文章的类型是否通过文本框输入来得到
        if (modelNew) {
            ArticleType type = articleTypeDAO.getArticleTypeByName(typeName);
            if (type == null) {
                type = new ArticleType();
                type.setName(typeName);
                type.setDisable(false);
                type.setCreateAt(new Date());
                type.setUpdateAt(new Date());
                articleTypeDAO.save(type);
                article.setType(type);
            } else {
                article.setType(type);
            }
        } else {
            ArticleType type = articleTypeDAO.getArticleTypeById(article.getType().getId());
            article.setType(type);
        }

        // 更新文件对象
        Date now = new Date();
        // 判断是否将文章保存为草稿或发表
        if (isDraft)
            article.setStatus(0);
        else
            article.setStatus(1);
        article.setUpdateAt(now);
        articleDAO.edit(article);
    }
    
    
    public void autoSave(Article article, boolean isEdit) {
        // 更新文件对象
        Date now = new Date();
        // 将文章保存为草稿
        article.setStatus(0);
        article.setUpdateAt(now);
        if(isEdit) {
            articleDAO.edit(article);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            String id = format.format(new Date());
            article.setId(id);
            article.setReadedNum(0);
            article.setCreateAt(now);
            articleDAO.save(article);
        }
        
    }

    public void addViewedCount(Article article) {
        long count = article.getReadedNum();
        count++;
        article.setReadedNum(count);
        articleDAO.update(article);
    }

    public Article get(String id) {
        Article article = articleDAO.getArticleById(id);
        return article;
    }

    public void delete(String id) {
        Article article = articleDAO.getArticleById(id);
        if (article != null) {
            articleDAO.delete(article);
        }
    }

    public Page<Article> getAllAricle(int pageNo, int pageSize) {
        Page<Article> page = articleDAO.getAllArticle(pageNo, pageSize);
        List<Article> list = page.getData();
        List<Article> articles = new ArrayList<Article>();
        for (Article article : list) {
            String content = article.getContent();
            // 去除HTML中的特别不显示的标签内容，去除HTML标签，转化其他字符为空格，并去除多余的空格
            content = content.replaceAll("(?i)<style([\\s\\S]+?)</style>|(?i)<script([\\s\\S]+?)</script>", "");
            content = content.replaceAll("<.*?>", "");
            content = content.replaceAll("(\\s)", " ");
            content = content.replaceAll("(\\s)+", "$1");
            // 用于省略文章的内容
            if (content.length() > 400) {
                content = content.substring(0, 400);
                content += "...";
            }
            article.setContent(content);
            articles.add(article);
        }
        page.setData(articles);
        return page;
    }

    public Page<Article> getAllAricleWithoutContent(int pageNo, int pageSize) {
        Page<Article> page = articleDAO.getAllArticle(pageNo, pageSize);
        return page;
    }

    public Page<Article> getAllDrafts(int pageNo, int pageSize) {
        return articleDAO.getAllDraft(pageNo, pageSize);
    }

    public Page<Article> getArticleByTypeAndTitle(int articleTypeId, String title, int pageNo, int pageSize) {
        return articleDAO.selectArticleByTypeAndTitle(articleTypeId, title, pageNo, pageSize);
    }

    public void updateAllowComment(String articleId, boolean allowComment) {
        Article article = articleDAO.getArticleById(articleId);
        if (article != null) {
            article.setAllowComment(allowComment);
            articleDAO.update(article);
        }
    }

    public void updateIsTop(String articleId, boolean isTop) {
        Article article = articleDAO.getArticleById(articleId);
        if (article != null) {
            article.setIsTop(isTop);
            articleDAO.update(article);
        }
    }

    public Page<Article> getArticleByTypeId(int typeId, int pageNo, int pageSize) {
        Page<Article> page = articleDAO.selectArticleByTypeId(typeId, pageNo, pageSize);
        List<Article> list = page.getData();
        List<Article> articles = new ArrayList<Article>();
        for (Article article : list) {
            String content = article.getContent();
            content = content.replaceAll("<.*?>", "");
            // 用于省略文章的内容
            if (content.length() > 250) {
                content = content.substring(0, 250);
                content += "...";
            }
            article.setContent(content);
            articles.add(article);
        }
        page.setData(articles);
        return page;
    }

    public List<Article> getReadedTop10() {
        return articleDAO.seletArticleTop10();
    }

    public List<Article> getAssociate5Articles(Article article) {
        return articleDAO.getAssociate5Articles(article);
    }

    public Article getNextArticle(Article article) {
        return articleDAO.getNextArticle(article);
    }

    public Article getPreviousArticle(Article article) {
        return articleDAO.getPreviousArticle(article);
    }
}
