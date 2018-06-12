<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
<title>博客管理-<spring:message code="page.title" /></title>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/lightbox.css" media="screen" />
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/ke4/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/ke4/lang/zh_CN.js"></script>
<style type="text/css">
table tbody td {
    vertical-align: middle !important;
}

#article_list, #type_list, #draft_list, #blog_images_list {
    min-height: 400px;
}
/* image列表样式 */
#blog_images_list {
    overflow: hidden;
}

#blog_images_list dl {
    float: left;
    margin: 3px;
    margin-bottom: 10px;
    text-align: center;
    width: 273px;
}

#blog_images_list dd {
    margin: 0px;
}

#blog_images_list dl dd  a {
    text-decoration: none;
}

#blog_images_list dl dd a img {
    padding: 2px;
    border: 1px #AAA solid;
}

#blog_images_list dl dt {
    font-weight: normal;
}
</style>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div id="blog-header" class="container">
        <h2>博客管理</h2>

        <div id="tabs" style="margin-bottom: 30px;">
            <ul class="nav nav-tabs">
                <li id="article_list_tag" class="active"><a href="javascript:void(0);">文章列表</a></li>
                <li id="type_list_tag"><a href="javascript:void(0);">类别管理</a></li>
                <li id="draft_list_tag"><a href="javascript:void(0);">草稿箱</a></li>
                <li id="blog_images_list_tag"><a href="javascript:void(0);">博客用图</a></li>
                <li id="configure_info_tag"><a href="javascript:void(0);">信息设置</a></li>
            </ul>
        </div>

        <div id="article_list">
            <div id="select_article">
                <form class="form-horizontal" role="form" onsubmit="return false;">
                    <div class="form-group">
                        <label class="col-sm-1 control-label" for="article_type">类别：</label>
                        <div class="col-sm-2">
                            <select name="article_type" class="form-control" id="article_type" onchange="select_condition_changed()">
                                <option value="0">全部</option>
                                <c:forEach items="${requestScope.articleTypes}" var="articleType">
                                    <option value="${articleType.id}">${articleType.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <label for="title" class="col-sm-1 control-label">标题：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="title" id="key_words" maxlength="30" onchange="select_condition_changed()" />
                        </div>
                    </div>
                </form>
            </div>
            <div id="article_list_table">
                <jsp:include page="/WEB-INF/pages/_article_tab.jsp" flush="true" />
            </div>
        </div>

        <!-- 文章类别  -->
        <div id="type_list" style="display: none">
            <jsp:include page="/WEB-INF/pages/_article_type_tab.jsp" flush="true" />
        </div>

        <!-- 草稿箱  -->
        <div id="draft_list" style="display: none">
            <jsp:include page="/WEB-INF/pages/_draft_tab.jsp" flush="true" />
        </div>

        <!-- 博客用图 -->
        <div id="blog_images_list" style="display: none">
            <jsp:include page="/WEB-INF/pages/_images_tab.jsp" flush="true" />
        </div>

        <div id="configure_info" style="display: none;">
            <div id="configure-errors" class="alert alert-danger" style="display: none;"></div>
            <div id="configure-messages" class="alert alert-success" style="display: none;"></div>
            <form:form action="handleInfo.html" id="blogInfoForm" method="post" modelAttribute="blogInfo" role="form" onsubmit="return false;">
                <form:hidden path="id" />
                <div class="form-group">
                    <label for="head"><spring:message code="page.label.head"/></label>
                    <form:input path="head" cssClass="form-control" placeholder="请输入博客名" />
                </div>
                <div class="form-group">
                    <label for="descriptions"><spring:message code="page.label.descriptions" /></label>
                    <form:input path="descriptions" cssClass="form-control" id="descriptions" placeholder="请求输入博客签名" />
                </div>
                <div class="form-group">
                    <label for="email"><spring:message code="page.label.email" /></label>
                    <form:input path="email" cssClass="form-control" placeholder="请求输入关联邮箱" />
                </div>
                <div class="form-group">
                    <label for="about"><spring:message code="page.label.about"/></label>
                    <form:textarea id="blog_description" path="about" cssClass="form-control" />
                </div>
                <button id="saveBlogInfoButton" type="submit" class="btn btn-default">保存</button>
            </form:form>
        </div>

        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/lightbox/lightbox.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/set.js" charset="utf-8"></script>
</body>
</html>