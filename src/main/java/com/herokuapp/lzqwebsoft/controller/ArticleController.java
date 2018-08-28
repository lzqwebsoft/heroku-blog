package com.herokuapp.lzqwebsoft.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.herokuapp.lzqwebsoft.exception.HttpNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticlePattern;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.CodeTheme;
import com.herokuapp.lzqwebsoft.pojo.Comment;
import com.herokuapp.lzqwebsoft.pojo.Page;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.service.CommentService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.MarkdownUtil;

@Controller
public class ArticleController extends BaseController {
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ArticlePattern patterns;

    @Autowired
    private CodeTheme themes;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/show/{articleId}.html")
    public String show(@PathVariable("articleId") String articleId, HttpSession session, ModelMap model, Locale locale) {
        Article article = articleService.get(articleId);
        if (article == null)
            throw new HttpNotFoundException();
        // 判断用户是否登录,未登录用户不可以查看草稿
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (user == null && article.getStatus() == 0)
            throw new HttpNotFoundException();

        // 阅读计数, 只有是没有登录的用户才进行记数
        if (session.getAttribute(CommonConstant.LOGIN_USER) == null) {
            Set<String> viewedArticles = (Set<String>) session.getAttribute(CommonConstant.VIEWED_ARTICLES);
            if (viewedArticles == null) {
                viewedArticles = new HashSet();
            }
            if (!viewedArticles.contains(articleId)) {
                viewedArticles.add(articleId);
                articleService.addViewedCount(article);
                session.setAttribute(CommonConstant.VIEWED_ARTICLES, viewedArticles);
            }
            // 用户未登录，则替换文章中内容使用的IMG标签SRC属性改为七牛云
            String domain = messageSource.getMessage("qiniu.bucket.domain", null, locale);
            String content = article.getContent();
            content = content.replaceAll("\\/images\\/show\\/(\\d{14}\\w{30}).(html|jpg)", domain + "$1");
            article.setContent(content);
        }
        model.addAttribute("article", article);

        List<Comment> comments = commentService.getAllParentComment(articleId);
        model.addAttribute("comments", comments);

        // 上篇文章
        Article previousArticle = articleService.getPreviousArticle(article);
        model.addAttribute("previousArticle", previousArticle);
        // 下篇文章
        Article nextArticle = articleService.getNextArticle(article);
        model.addAttribute("nextArticle", nextArticle);
        // 5篇关联的文章
        List<Article> ass_articles = new ArrayList<Article>();
        if (article.getType() != null && article.getType().getId() != 0) {
            ass_articles = articleService.getAssociate5Articles(article);
        }
        model.addAttribute("associate5Articles", ass_articles);

        Comment comment = new Comment();
        comment.setArticle(article);
        model.addAttribute("comment", comment);
        return "show";
    }

    @RequestMapping("/article/new{type}.html")
    public String create(@PathVariable("type") String type, ModelMap model) {
        Article article = new Article();
        article.setAllowComment(true);
        String view = "new-md";
        if (type != null && type.equals("html")) {
            article.setContentType(0); // HTML
            view = "new-w";
        } else if (type == null || type.trim().equals("")) {
            article.setContentType(1); // markdown
        } else {
            throw new HttpNotFoundException();
        }
        model.addAttribute("article", article);

        model.addAttribute("patterns", patterns);
        model.addAttribute("codeThemes", themes);

        model.addAttribute("editOrCreate", "CREATE");

        List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
        model.addAttribute("articleTypes", articleTypes);
        return view;
    }

    @RequestMapping(value = "/article/publish.html", method = RequestMethod.POST)
    public String publish(@ModelAttribute("article") Article article, Errors errors, int type_model, String new_type, ModelMap model, HttpSession session,
                          String publish, String save, String editOrCreate, Locale locale) {
        // 验证用户提交数据的合法性
        String articleContentLabel = messageSource.getMessage("page.label.article.content", null, locale);
        String articleTitleLabel = messageSource.getMessage("page.label.title", null, locale);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "info.required", new Object[]{articleTitleLabel});
        boolean isConvert = save != null && save.trim().equals("2");   // 判断是否为转化内容
        if (article == null) {
            return "redirect:/";
        }
        if (article.getContentType() == 1) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contentMD", "info.required", new Object[]{articleContentLabel});
            article.setContent(MarkdownUtil.parseMarkdownToHtml(article.getContentMD()));
            if (isConvert)
                article.setContentType(0);
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "info.required", new Object[]{articleContentLabel});
            article.setContentMD(MarkdownUtil.parseHtmlToMarkdown(article.getContent()));
            if (isConvert)
                article.setContentType(1);
        }
        int patternTypeId = article.getPatternTypeId();
        if (patternTypeId == 0) {
            String patternLabel = messageSource.getMessage("page.label.pattern", null, locale);
            errors.rejectValue("patternTypeId", "info.select", new Object[]{patternLabel}, "");
        }

        int type = type_model; // 文章类型添加的方式，为0表示通过下拉框选择，1表示再创建
        if (type == 0) {
            // 当为选择一个类别时
            ArticleType articleType = article.getType();
            if (articleType == null || articleType.getId() == 0) {
                String articleTypeLabel = messageSource.getMessage("page.label.article.type", null, locale);
                errors.rejectValue("type.id", "info.select", new Object[]{articleTypeLabel}, "");
            } else {
                articleType = articleTypeService.get(articleType.getId());
                if (articleType == null) {
                    errors.rejectValue("type", "info.select.articleType.notExist");
                }
            }
        } else if (type == 1) {
            // 当为创建一个类别时
            if (new_type == null || new_type.trim().length() <= 0) {
                String articleTypeLabel = messageSource.getMessage("page.label.article.type", null, locale);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type.name", "info.required", new Object[]{articleTypeLabel});
            }
        }

        // 判断是否有错误
        if (errors.hasErrors()) {
            List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
            model.addAttribute("articleTypes", articleTypes);

            model.addAttribute("patterns", patterns);
            model.addAttribute("codeThemes", themes);
            return "new";
        } else {
            // 如果为添加类型，则先创建一个新的类型
            boolean modelNew = type == 1 && new_type != null && new_type.trim().length() > 0;
            // 判断是否为草稿内容
            boolean isDraft = publish == null || publish.trim().length() <= 0;
            // 添加作者
            User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
            article.setAuthor(user);
            // 入库
            if (editOrCreate != null && editOrCreate.equals("EDIT")) {
                articleService.update(article, new_type, modelNew, isDraft);
            } else {
                articleService.save(article, new_type, modelNew, isDraft);
            }

            if (isConvert) {
                return "redirect:/edit/" + article.getId() + ".html";
            } else {
                return "redirect:/show/" + article.getId() + ".html";
            }
        }
    }

    // ================== 主要用于文章编辑页面的AJAX自动保存处理=============
    @ResponseBody
    @RequestMapping(value = "/article/autoSave.html", method = RequestMethod.POST)
    public String autoSave(@ModelAttribute("article") Article article, HttpSession session, String editOrCreate, HttpServletResponse response, Locale locale) {
        String content = article.getContent();
        String contentMD = article.getContentMD();
        boolean status = false;
        if ((article.getContentType() == 0 && content != null && content.trim().length() > 0) ||
                (article.getContentType() == 1 && contentMD != null && contentMD.trim().length() > 0)) {
            // 设置文章默认为原创
            int patternTypeId = article.getPatternTypeId();
            if (patternTypeId == 0) {
                article.setPatternTypeId(1);
            }
            // 转化内容
            if (article.getContentType() == 1) {
                article.setContent(MarkdownUtil.parseMarkdownToHtml(contentMD));
            } else if (article.getContentType() == 0) {
                article.setContentMD(MarkdownUtil.parseHtmlToMarkdown(content));
            }
            // 设置文章类型默认为空
            article.setType(null);
            // 添加作者
            User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
            article.setAuthor(user);
            // 入库
            boolean isEdit = editOrCreate != null && editOrCreate.equals("EDIT");
            articleService.autoSave(article, isEdit);
            status = true;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        if (status) {
            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String successMessage = messageSource.getMessage("info.article.autoSaveSuccess", new Object[]{nowTime}, locale);
            Map<String, Object> datas = new HashMap<String, Object>();
            datas.put("article_id", article.getId());
            return successJSON(successMessage, datas);
        } else {
            String failureMessage = messageSource.getMessage("info.article.autoSaveFailure", null, locale);
            return errorJSON(failureMessage);
        }
    }

    @RequestMapping("/edit/{articleId}.html")
    public String edit(@PathVariable("articleId") String articleId, ModelMap model) {
        Article article = articleService.get(articleId);
        model.addAttribute("article", article);

        model.addAttribute("patterns", patterns);
        model.addAttribute("codeThemes", themes);
        model.addAttribute("editOrCreate", "EDIT");

        List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
        model.addAttribute("articleTypes", articleTypes);

        return article.getContentType() == 0 ? "new-w" : "new-md";
    }

    @RequestMapping("/delete/{articleId}.html")
    public String delete(@PathVariable("articleId") String articleId, HttpServletRequest request) {
        articleService.delete(articleId);

        String referer = request.getHeader("referer");
        if (referer != null && referer.trim() != "" && !referer.matches("(?i)^.*/(show/.*|signIn).html$")) {
            return "redirect:" + referer;
        } else {
            return "redirect:/index.html";
        }
    }

    // ================== 主要用于set页面的AJAX处理=============
    @RequestMapping("/delete/article/{articleId}.html")
    public String deleteArticle(@PathVariable("articleId") String articleId, int articleTypeId, String title, int pageNo, HttpServletRequest request) {
        if (pageNo <= 0)
            pageNo = 1;

        articleService.delete(articleId);
        Page<Article> articles = articleService.getArticleByTypeAndTitle(articleTypeId, title, pageNo, 15);

        // 如果删除的一条数据刚好是这一页的最后一条数据，则显示上页
        if (pageNo > 1 && articles.getData().size() <= 0) {
            articles = articleService.getArticleByTypeAndTitle(articleTypeId, title, pageNo - 1, 15);
        }

        request.setAttribute("page", articles);

        request.setAttribute("articleTypeId", articleTypeId);
        request.setAttribute("title", title);

        return "_article_tab";
    }

    @RequestMapping("/delete/draft/{articleId}.html")
    public String deleteDraft(@PathVariable("articleId") String articleId, int pageNo, HttpServletRequest request) {
        if (pageNo <= 0)
            pageNo = 1;

        articleService.delete(articleId);
        // 所有的草稿
        Page<Article> page_drafts = articleService.getAllDrafts(pageNo, 15);

        // 如果删除的一条数据刚好是这一页的最后一条数据，则显示上页
        if (pageNo > 1 && page_drafts.getData().size() <= 0)
            page_drafts = articleService.getAllDrafts(pageNo - 1, 15);

        request.setAttribute("page_drafts", page_drafts);

        return "_draft_tab";
    }

    @RequestMapping("/draft/page.html")
    public String pageDraft(Integer pageNo, HttpServletRequest request) {
        if (pageNo == null || pageNo <= 0)
            pageNo = 1;

        // 所有的草稿
        Page<Article> page_drafts = articleService.getAllDrafts(pageNo, 15);
        request.setAttribute("page_drafts", page_drafts);

        return "_draft_tab";
    }

    @RequestMapping("/article/select.html")
    public String select(int articleTypeId, String title, Integer pageNo, HttpServletRequest request) {
        if (pageNo == null || pageNo <= 0)
            pageNo = 1;

        Page<Article> page = articleService.getArticleByTypeAndTitle(articleTypeId, title, pageNo, 15);
        request.setAttribute("page", page);

        request.setAttribute("articleTypeId", articleTypeId);
        request.setAttribute("title", title);

        return "_article_tab";
    }

    @RequestMapping("/update/allow_comment/{articleId}.html")
    public void updateAllowComment(@PathVariable("articleId") String articleId, boolean allowComment, HttpServletResponse response) {
        articleService.updateAllowComment(articleId, allowComment);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping("/update/is_top/{articleId}.html")
    public void updateIsTop(@PathVariable("articleId") String articleId, boolean isTop, HttpServletResponse response) {
        articleService.updateIsTop(articleId, isTop);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
