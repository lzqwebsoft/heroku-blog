package com.herokuapp.lzqwebsoft.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.herokuapp.lzqwebsoft.util.SearchUtils;
import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.ArticleDAO;
import com.herokuapp.lzqwebsoft.dao.ArticleTypeDAO;
import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.Page;

import static java.util.stream.Collectors.toList;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleTypeDAO articleTypeDAO;

    @Autowired
    private ArticleDAO articleDAO;

    public void initializeHibernateSearch() {
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(articleDAO.getSession());
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void save(Article article, String typeName, boolean modelNew, boolean isDraft) {
        saveOrUpdate(article, typeName, modelNew, isDraft, true);
    }

    public void update(Article article, String typeName, boolean modelNew, boolean isDraft) {
        saveOrUpdate(article, typeName, modelNew, isDraft, false);
    }

    private void saveOrUpdate(Article article, String typeName, boolean modelNew, boolean isDraft, boolean isNew) {
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

        // 判断是否将文章保存为草稿或发表
        if (isDraft)
            article.setStatus(0);
        else
            article.setStatus(1);

        Date now = new Date();
        if (isNew) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            String id = format.format(new Date());
            article.setId(id);
            article.setCreateAt(now);
            article.setReadedNum(0);
        }
        article.setUpdateAt(now);
        if (isNew) {
            articleDAO.save(article);
        } else {
            articleDAO.edit(article);
        }
    }


    public void autoSave(Article article, boolean isEdit) {
        // 更新文件对象
        Date now = new Date();
        // 将文章保存为草稿
        article.setStatus(0);
        article.setUpdateAt(now);
        if (isEdit) {
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
        list = list.stream().map(article -> decodeContent(article)).collect(toList());
        page.setData(list);
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
        list = list.stream().map(article -> decodeContent(article)).collect(toList());
        page.setData(list);
        return page;
    }

    public Page<Article> search(String keyword, int pageNo, int pageSize) {
        FullTextSession fts = Search.getFullTextSession(articleDAO.getSession());
        QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
        Query luceneQuery = qb.bool().must(qb.keyword().onField("status").matching(1).createQuery()) // 已发布的
                .must(qb.keyword().onFields("title", "content").matching(keyword).createQuery()).createQuery();
        FullTextQuery query = fts.createFullTextQuery(luceneQuery, Article.class);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        // 得到数据并将数据高亮
        List<Article> list = SearchUtils.hightLightArtilce(luceneQuery, query.list(), 400, "title", "content");
        // 封装分页数据
        Page<Article> model = new Page(query.getFirstResult(), query.getResultSize(), pageSize, 5, list);
        model.setData(list);

        return model;
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

    // 转化文章内容去HTML，并省略为400字
    private Article decodeContent(Article article) {
        String content = article.getContent();
        content = content.replaceAll("(?i)<style([\\s\\S]+?)</style>|(?i)<script([\\s\\S]+?)</script>", "");
        content = content.replaceAll("<.*?>", "");
        content = content.replaceAll("(\\s)+", " ");
        // 用于省略文章的内容
        if (content.length() > 400) {
            content = content.substring(0, 400);
            content += "...";
        }
        article.setContent(content);
        return article;
    }

    @Override
    public List<Article> getTotalArticles() {
        return articleDAO.getTotalArticles();
    }

    @Override
    public void update(Article article) {
        articleDAO.update(article);
    }
}
