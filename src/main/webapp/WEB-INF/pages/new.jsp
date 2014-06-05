<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑博客-<spring:message code="page.title"/></title>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/ke4/themes/default/default.css" />
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/ke4/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/resources/js/ke4/lang/zh_CN.js"></script>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div id="blog-header" class="container">
        <h2>撰写博客</h2>

        <div id="prompt_info" class="alert alert-danger hidden"></div>
        <form:form action="${pageContext.request.contextPath}/article/publish.html" onsubmit="return check_article_input();" method="post" modelAttribute="article">
            <div class="form-group">
                <label for="article_title">标题</label>
                <div class="row">
                    <div class="col-sm-2">
                        <form:select path="patternTypeId" cssClass="form-control">
                            <option value="0">请选择</option>
                            <form:options items="${patterns.patterns}" />
                        </form:select>
                        <form:hidden path="id" />
                    </div>
                    <div class="col-sm-10">
                        <form:input path="title" cssClass="form-control" id="article_title" placeholder="请输入文章标题" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="article_content">博客内容</label>
                <form:textarea path="content" id="article_content" cssClass="form-control" rows="4" />
            </div>
            <div class="form-group">
                <label for="article_type">文章类别</label>
                <div class="row">
                    <div class="col-sm-3">
                        <form:select id="type" path="type.id" cssClass="form-control">
                            <option value="0">请选择</option>
                            <form:options items="${articleTypes}" itemLabel="name" itemValue="id" />
                        </form:select>
                        <input id="new_type" name="new_type" type="text" class="form-control" style="display: none" placeholder="请输入新的分类名称" />
                    </div>
                    <div class="col-sm-2" style="padding: 6px 12px;">
                        <input id="type_model" name="type_model" type="hidden" value="0" /> <a href="javascript:void(0)" onclick="change_type(this); return false;">新建分类</a>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="codeTheme">代码主题</label>
                <div class="row">
                    <div class="col-sm-3">
                        <form:select path="codeTheme" cssClass="form-control">
                            <form:options items="${codeThemes.themes}" />
                        </form:select>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-sm-2">
                        <form:radiobutton path="allowComment" value="true" />允许评论
                    </div>
                    <div class="col-sm-2">
                        <form:radiobutton path="allowComment" value="false" /> 禁止评论
                    </div>
                    <form:hidden path="isTop" />
                    <form:hidden path="readedNum" />
                </div>
            </div>
            <input type="hidden" name="editOrCreate" value="${editOrCreate}" />
            <button type="submit" name="publish" value="1" class="btn btn-primary">发表</button>
            <button type="submit" name="save" value="1" class="btn btn-default">保存为草稿</button>
        </form:form>

        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>


    <%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/new.js"></script>
</body>
</html>