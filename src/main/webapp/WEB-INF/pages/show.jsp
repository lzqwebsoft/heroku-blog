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
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/prism.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/share.css?_v=1.0" />
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/wangEditor.min.js"></script>

<style type="text/css">
/* 代码块CSS */
pre.my_pre {
   /* 可以自行添加 */
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
    background-image: none;
    padding-left: 4px;
    box-shadow: none;
}
.article_comment a {
    margin-right: 5px;
}
.child_comment .comment_content p {
    display: inline;
}
code {
    white-space: normal;
}
#article_content p {
    margin: 0 0 5px
}
#article_content blockquote {
    padding: 10px 15px;
    margin: 10px 0 10px;
    font-size: inherit;
    color: #666;
}
#article_content h1 {
    font-size: 24px;
}
#article_content h2 {
    font-size: 22px;
}
#article_content h3 {
    font-size: 20px;
}
#article_content h4 {
    font-size: 18px;
}
#article_content h5 {
    font-size: 16px;
}
.pager {
    padding: 0px 10px;
}
.previous a, .next a{
    max-width: 49%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap; 
}
.next a {
    direction: rtl;
    text-align: left;
}
@media screen and (min-width: 768px) {
    #validateCodeZone span {
        padding-left: 0;
    }
}
@media screen and (max-width: 767px) {
    #validateCodeZone span{
        margin-top: 10px;
    }
}
#content {
    display: none;
}
.w-e-toolbar {
    flex-wrap: wrap;
    -webkit-box-lines: multiple;
    background-color: #FFF !important;
    border-radius: 4px 4px 0 0;
}
.w-e-text-container {
    border-radius: 0 0 4px 4px;
    height: 100px !important;
}
.w-e-text {
    overflow-y: hidden;
}
</style>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <div class="container" id="blog-header">
        <div class="page-header">
            <c:if test="${requestScope.blogInfo!=null}">
                <h1>
                    ${requestScope.blogInfo.head}&nbsp;<small>${requestScope.blogInfo.descriptions}</small>
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
        <div class="row article_time_detail">
            <div class="col-xs-8" style="font-size:14px; padding: 0;">发表于：<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />，已有${article.readedNum}次阅读</div>
            <div class="col-xs-4 text-right" style="padding: 0;">
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
        <div class="share_zone">
            <div id="sns_share" class="cf">
                <span class="sns_share_to fl">分享到：</span>
                <a class="share_weixin share_icon fl" href="javascript:void(0);" title="查看本文二维码，分享至微信"><em>二维码</em></a>
                <a class="share_tsina share_icon fl" href="javascript:void(0);" title="分享到新浪微博"><em>新浪微博</em></a>
                <a class="share_tqzone share_icon fl" href="javascript:void(0);" title="分享到QQ空间"><em>QQ空间</em></a>
                <a class="share_twitter share_icon fl" href="javascript:void(0);" title="分享到Twitter"><em>twitter</em></a>
                <a class="share_facebook share_icon fl" href="javascript:void(0);" title="分享到Facebook"><em>Facebook</em></a>
    
                <div class="wemcn" id="wemcn">
                    <div id="ewm" class="ewmDiv clearfix">
                        <div class="rwmtext">
                            <p>扫一扫，用手机观看！</p>
                            <p>用微信扫描还可以</p>
                            <p>分享至好友和朋友圈</p>
                        </div>
                        <div class="qrcode"></div>
                        <img id='ewmimg' class='ewmimg' width='85' height='85' alt='二维码分享' />
                    </div>
                    <a class="share_icon" href="javascript:void(0)" id="ewmkg"></a>
                    <i class="ewmsj share_icon"></i>
                </div>
            </div>
        </div>

        <div class="row">
            <ul class="pager">
                <c:if test="${previousArticle!=null}">
                    <li class="previous"><a href="${pageContext.request.contextPath}/show/${previousArticle.id}.html">&larr; ${previousArticle.title}</a></li>
                </c:if>
                <c:if test="${nextArticle!=null}">
                    <li class="next"><a href="${pageContext.request.contextPath}/show/${nextArticle.id}.html">&rarr; ${nextArticle.title}</a></li>
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
                                    <div id="comment_content"></div>
                                    <form:textarea id="content" path="content" cssClass="form-control" rows="4" />
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/prism.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/share.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/show.js?_v=1.1.4"></script>
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
</body>
</html>