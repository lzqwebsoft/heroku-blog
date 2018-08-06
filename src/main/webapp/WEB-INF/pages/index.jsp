<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="org.springframework.web.util.UrlPathHelper"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
<title><spring:message code="page.title" /></title>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
<style type="text/css">
/*
 * Off Canvas
 * --------------------------------------------------
 */
@media screen and (max-width: 767px) {
   body {
       font-size:16px;
   }
  .row-offcanvas {
    position: relative;
    -webkit-transition: all 0.25s ease-out;
    -moz-transition: all 0.25s ease-out;
    transition: all 0.25s ease-out;
  }

  .row-offcanvas-right .sidebar-offcanvas {
    right: -50%; /* 6 columns */
  }

  .row-offcanvas-left .sidebar-offcanvas {
    left: -50%; /* 6 columns */
  }

  .row-offcanvas-right.active {
    right: 50%; /* 6 columns */
  }

  .row-offcanvas-left.active {
    left: 50%; /* 6 columns */
  }

  .sidebar-offcanvas {
    position: absolute;
    overflow-x: hidden;
    top: 0;
    width: 50%; /* 6 columns */
  }
}
</style>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div id="blog-header" class="container" style="overflow-x: hidden;">
        <div class="page-header">
            <c:if test="${requestScope.blogInfo!=null}">
                <h1>
                    ${requestScope.blogInfo.head}&nbsp;<small>${requestScope.blogInfo.descriptions}</small>
                </h1>
            </c:if>
        </div>
        <div class="row row-offcanvas row-offcanvas-right">
            <p class="pull-right visible-xs">
                <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">分类</button>
            </p>
            <div class="col-xs-12 col-sm-9 blog-list">
                
                <c:choose>
                    <c:when test="${page!=null&&page.data!=null&&fn:length(page.data)>0}">
                        <c:forEach items="${page.data}" var="article">
                            <div class="media">
                                <div class="media-body">
                                    <h4 class="media-heading article-index-title">
                                        <c:out value="${article.patternTypeLabel}" escapeXml="false" /> <a href="${pageContext.request.contextPath}/show/${article.id}.html"><c:out value="${article.title}" /></a>
                                        <c:if test="${article.isTop}">
                                            <span class="label label-danger">置顶</span>
                                        </c:if>
                                    </h4>
                                    <p class="article_time">发表于：<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                                    <p style="text-indent: 2em;">
                                        <c:out value="${article.content}" escapeXml="false" />
                                    </p>
                                    <p class="article-operate-zone">
                                        
                                        <a class="btn btn-default" role="button" href="show/${article.id}.html">阅读(${article.readCountLabel})</span></a> <a class="btn btn-default" role="button" href="show/${article.id}.html#reply_comment">评论(${article.commentCount})</span></a>
                                        <c:if test="${sessionScope.user!=null}">
                                            <a class="btn btn-danger pull-right" style="margin-right: 0;" role="button" href="<%=request.getContextPath()%>/delete/${article.id}.html" onclick="return confirm('<spring:message code="page.confirm.delete.article" arguments="${fn:escapeXml(article.title)}"  />');">删除</a>
                                            <a class="btn btn-primary pull-right" role="button" href="edit/${article.id}.html">编辑</a>
                                        </c:if>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>暂无内容</p>
                    </c:otherwise>
                </c:choose>
                <!-- 翻页 -->
                <c:if test="${page!=null&&page.data!=null&&fn:length(page.data)>0}">
                    <ul class="pagination">
                        <c:if test="${page.hasPreviousPage}">
                            <li><a href="<%= new UrlPathHelper().getOriginatingRequestUri(request) %>?pageNo=${page.currentPageNo-1}">&laquo;</a></li>
                        </c:if>
                        <c:forEach var="index" begin="${page.pageRangeStart}" end="${page.pageRangeEnd}" step="1">
                            <c:choose>
                                <c:when test="${index!=page.currentPageNo}">
                                    <li><a href="<%= new UrlPathHelper().getOriginatingRequestUri(request) %>?pageNo=${index}">${index}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="active"><a>${index}<span class="sr-only">(current)</span></a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${page.hasNextPage}">
                            <li><a href="<%= new UrlPathHelper().getOriginatingRequestUri(request) %>?pageNo=${page.currentPageNo+1}">&raquo;</a></li>
                        </c:if>
                    </ul>
                    <div id="page_description">${page.totalCount}篇文章, 共${page.totalPageCount}页</div>
                </c:if>
            </div>

            <!-- 右导航菜单 -->
            <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
                <div class="panel panel-default">
                    <div class="panel-heading">文章分类</div>
                    <c:choose>
                    <c:when test="${articleTypes!=null&&fn:length(articleTypes)>0}">
                    <div class="list-group">
                        <c:forEach items="${articleTypes}" var="articleType">
                            <c:if test="${!articleType.disable}">
                            <a href="<%= request.getContextPath() %>/select/${articleType.id}.html" class="list-group-item <c:if test="${requestScope.articleTypeId!=null && requestScope.articleTypeId==articleType.id}"><c:out value="active" /></c:if>">${articleType.name}<span class="badge">${fn:length(articleType.articles)}</span></a>
                            </c:if>
                        </c:forEach>
                    </div>
                    </c:when>
                    <c:otherwise>
                        <p>暂无</p>
                    </c:otherwise>
                    </c:choose>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">阅读排行</div>
                    <c:choose>
                        <c:when test="${top10Articles!=null&&fn:length(top10Articles)>0}">
                            <div class="list-group">
                                <c:forEach items="${top10Articles}" var="top10">
                                    <a href="<%=request.getContextPath()%>/show/${top10.id}.html" title="${top10.title}" class="list-group-item" style="overflow: hidden;">${top10.title}<span class="badge pull-right">${top10.readCountLabel}</span></a>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="panel-body"><p>暂无</p></div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">相关链接</div>
                    <div class="list-group">
                        <a href="http://blog.csdn.net/lzqwebsoft" class="list-group-item glyphicon glyphicon-link"> CSDN</a>
                        <a href="http://my.oschina.net/websoft" class="list-group-item glyphicon glyphicon-link"> 开源中国</a>
                        <a href="http://weibo.com/lzqwebsoft" class="list-group-item glyphicon glyphicon-link"> 新浪微博</a>
                        <a href="https://www.facebook.com/lzqwebsoft" class="list-group-item glyphicon glyphicon-link"> Facebook</a>
                        <a href="https://twitter.com/lzqwebsoft" class="list-group-item glyphicon glyphicon-link"> Tiwtter</a>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">联系我</div>
                    <div class="list-group" style="font-size: 14px;">
                        <a href="mailto:lzqwebsoft@gmail.com" class="list-group-item glyphicon glyphicon-envelope"> lzqwebsoft@gmail.com</a>
                        <a href="mailto:751939573@qq.com" class="list-group-item glyphicon glyphicon-envelope"> 751939573@qq.com</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- 页面底端说明 -->
        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>

<%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
  $('[data-toggle=offcanvas]').click(function() {
    $('.row-offcanvas').toggleClass('active');
  });
});
</script>
</body>
</html>