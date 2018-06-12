<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
<title>${article.title}-<spring:message code="page.title" /></title>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
<c:choose>
    <c:when test="${article.codeTheme==null}">
        <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/shl/styles/shCoreDefault.css" />
        <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/shl/styles/shThemeDefault.css" />
    </c:when>
    <c:otherwise>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/js/shl/styles/shCore${article.codeTheme}.css" />
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/js/shl/styles/shTheme${article.codeTheme}.css" />
    </c:otherwise>
</c:choose>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/shl/styles/shCore.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/shl/scripts/shCore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/shl/scripts/shAutoloader.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/ke4/themes/default/default.css" />
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/ke4/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/ke4/lang/zh_CN.js"></script>

<style type="text/css">
/* 代码块CSS */
.my_pre {
    display: block;
    border: 1px solid #CCCCCC;
    border-radius: 4px;
    line-height: 1.42857;
    font-size: 13px;
    margin: 0 0 10px;
    padding: 9.5px;
    word-break: break-all;
    word-wrap: break-word;
}
/* 文章目录CSS */
.bs-sidebar {
    margin-top: 10px;
    padding-top: 10px;
    padding-bottom: 10px;
    text-shadow: 0 1px 0 #fff;
    background-color: #f7f5fa;
    border-radius: 5px;
}

.bs-sidenav-title {
    padding: 5px 10px;
    cursor: pointer;
}

.bs-sidenav-title:hover {
    color: #563d7c;
    background-color: transparent;
    border-right: 1px solid #563d7c;
    background-color: #EEEEEE;
}
#article_content img {
    max-width: 100%;
}
/* All levels of nav */
.bs-sidebar .nav li a {
    display: block;
    color: #716b7a;
    padding: 5px 20px;
}
/* Nav: second level (shown on .active) */
.bs-sidebar .nav .nav {
    margin-bottom: 8px;
}

.bs-sidebar .nav .nav>li>a {
    padding-top: 3px;
    padding-bottom: 3px;
    padding-left: 40px;
    font-size: 90%;
}

/* 评论CSS */
.root_comment {
    margin-bottom: 10px;
}

.root_comment p,#prompt_replay_info p {
    background-image: linear-gradient(to bottom, #F5F5F5 0px, #E8E8E8 100%);
    background-repeat: repeat-x;
    border-color: #DCDCDC;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05) inset, 0 1px 0 rgba(255, 255, 255, 0.1);
    border-radius: 3px;
    padding: 5px;
    background-color: #F5F5F5;
    min-height: 20px;
    margin: 2px 0 5px 0;
}

.child_comment {
    margin-left: 20px;
    margin-bottom: 10px;
}

.comment_content,.comment_content p {
    background-color: #FFF;
    padding-left: 4px;
}
code {
    white-space: normal;
}
.my_pre code {
    white-space: nowrap;
}
@media screen and (max-width: 767px) {
   body {
       font-size:16px;
   }
}
</style>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <div class="container" id="blog-header">
        <div class="page-header">
            <c:if test="${requestScope.blogInfo!=null}">
                <h1>
                    ${requestScope.blogInfo.head} <small>${requestScope.blogInfo.descriptions}</small>
                </h1>
            </c:if>
        </div>

        <ol class="breadcrumb">
            <li><a href="<c:url value="/" />">首页</a></li>
            <li><a href="<%= request.getContextPath() %>/select/${article.type.id}.html"><c:out value="${article.type.name}" /></a></li>
            <li class="active">博客正文</li>
        </ol>

        <h3 class="media-heading article-show-title">
            <c:out value="${article.patternTypeLabel}" escapeXml="false" />
            <c:out value="${article.title}" />
            <c:if test="${article.isTop}">
                <span class="label label-danger">置顶</span>
            </c:if>
        </h3>
        <div class="row">
            <div class="col-xs-8" style="font-size:14px;">发表于：<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />，已有${article.readedNum}次阅读</div>
            <div class="col-xs-4 text-right">
                <a class="btn btn-default btn-sm" role="button" href="#reply_comment">评论(${fn:length(comments)})</a>
                <c:if test="${sessionScope.user!=null}">
                    <a class="btn btn-primary btn-sm" role="button" href="<%=request.getContextPath()%>/edit/${article.id}.html">编辑</a>
                    <a class="btn btn-danger btn-sm" role="button" href="<%=request.getContextPath()%>/delete/${article.id}.html" onclick="return confirm('<spring:message code="page.confirm.delete.article" arguments="${fn:escapeXml(article.title)}"  />');">删除</a>
                </c:if>
            </div>
        </div>

        <!-- 文章目录，自动生成 -->
        <div id="table_of_contents" class="row hidden">
            <div class="col-md-6">
                <div class="bs-sidebar hidden-print" role="complementary">
                    <div class="bs-sidenav-title" data-toggle="collapse" data-target="#auto_contents">
                        <b>目录</b>
                    </div>
                    <ul id="auto_contents" class="nav bs-sidenav collapse">
                    </ul>
                </div>
            </div>
        </div>

        <!-- 文章内容 -->
        <div id="article_content" style="margin-top: 10px; word-break:break-all;">
            <c:out value="${article.content}" escapeXml="false" />
        </div>
        <div class="bdsharebuttonbox">
            <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
            <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
            <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
            <a href="#" class="bds_fbook" data-cmd="fbook" title="分享到Facebook"></a>
            <a href="#" class="bds_twi" data-cmd="twi" title="分享到Twitter"></a>
            <a href="#" class="bds_more" data-cmd="more"></a>
            <a class="bds_count" data-cmd="count"></a>
        </div>

        <div class="row">
            <ul class="pager">
                <c:if test="${previousArticle!=null}">
                    <li class="previous"><a href="${pageContext.request.contextPath}/show/${previousArticle.id}.html">&larr; ${previousArticle.title}</a></li>
                </c:if>
                <c:if test="${nextArticle!=null}">
                    <li class="next"><a href="${pageContext.request.contextPath}/show/${nextArticle.id}.html">${nextArticle.title} &rarr;</a></li>
                </c:if>
            </ul>
        </div>

        <div id="assume_you_like" class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-toggle="collapse" href="#collapseTwo">相关文章</a>
                </h4>
            </div>
            <div id="collapseTwo" class="list-group panel-collapse collapse in">
                <c:choose>
                    <c:when test="${associate5Articles!=null&&fn:length(associate5Articles)>0}">
                        <c:forEach items="${associate5Articles}" var="assArticles">
                            <a href="${pageContext.request.contextPath}/show/${assArticles.id}.html" class="list-group-item">${assArticles.title} (<fmt:formatDate value="${assArticles.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />)
                            </a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise><p class="list-group-item">暂无</p></c:otherwise>
                </c:choose>
            </div>
        </div>

        <c:if test="${article.allowComment}">
        <div class="panel panel-default">
            <div class="panel-heading">评论</div>
            <div class="panel-body">
                <div id="article_comment" class="article_comment">
                    <jsp:include page="/WEB-INF/pages/_article_comments.jsp" />
                </div>

                <div class="panel panel-info">
                    <div class="panel-body">
                        <div id="add-comment-info-div" class="alert alert-danger hidden"></div>
                        <div id="prompt_replay_info" style='display: none'>
                            <p>
                                <span id="info_prompt_message">回复：&nbsp;&nbsp;</span> <span id="cancel_replay_button"> <a href="javascript:void(0)" class="pull-right" onclick="cancel_replay_comment();">取消</a>
                                </span>
                            </p>
                        </div>
                        <form:form action="${pageContext.request.contextPath}/comment/add.html" id="reply_comment" cssClass="form-horizontal" method="post" modelAttribute="comment">
                            <form:hidden path="article.id" />
                            <form:hidden path="article.title" />
                            <input type="hidden" name="parent_comment_id" id="parent_comment_id" />
                            <input type="hidden" name="root_comment_id" id="root_comment_id" />
                            <c:if test="${sessionScope.user==null}">
                            <div class="form-group">
                                <div class="col-sm-7">
                                    <form:input path="reviewer" cssClass="form-control" id="reviewer" placeholder="请输入昵称" />
                                </div>
                                <label for="reviewer" class="col-sm-5">昵称<font color='red'>*</font></label>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-7">
                                    <form:input path="website" cssClass="form-control" id="website" placeholder="请输入个人网站" />
                                </div>
                                <label for="website" class="col-sm-5">个人网站</label>
                            </div>
                            </c:if>
                            <div class="form-group">
                                <div class="col-sm-7">
                                    <form:textarea id="comment_content" path="content" cssClass="form-control" rows="4" />
                                    <span id="wordcount" class="help-block pull-right">您还可输入<span id="str">120</span>个字</span>
                                </div>
                            </div>
                            <div class="form-group hidden" id="validateCodeZone">
                                 <div class="col-sm-2">
                                    <input type="text" name="validateCode" id="validateCode" class="form-control" placeholder="图片验证码" />
                                 </div>
                                 <span class="col-sm-4 pull-left"><img id="captcha-image" src="${pageContext.request.contextPath}/captcha.html" title="验证码" alt="验证码" /><a href="javascript:void(0)" style="margin-left: 10px;" id="update-captcha-link">看不清，换一张</a></span>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <button type="submit" class="btn btn-primary">发表评论</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        </c:if>

        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/show.js?_v=1.0.0"></script>
    <script type="text/javascript">
        $(function() {
            $("#update-captcha-link").click(function() {
                $("#captcha-image").attr("src", "${pageContext.request.contextPath}/captcha.html?"+ Math.floor(Math.random()*100));
            });
            $("#captcha-image").click(function() {
                $(this).attr("src", "${pageContext.request.contextPath}/captcha.html?"+ Math.floor(Math.random()*100));
            });
        });
    </script>
    <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
</body>
</html>