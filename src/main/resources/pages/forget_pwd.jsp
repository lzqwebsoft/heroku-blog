<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
    <title>找回密码-<spring:message code="page.title" /></title>
    <%@ include file="/pages/common/default_css.jsp"%>
</head>

<body>
    <%@ include file="/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div id="blog-header" class="container">
        <h2><spring:message code="page.label.foundPwd" /></h2>
        <h3><small><spring:message code="page.label.foundPwd.prompt.info" /></small></h3>

        <form action="<c:url value="/found_pwd.html" />" method="post" class="form-horizontal" role="form">
          <p style="color: red;">${errorInfo}</p>
          <div class="form-group">
            <label for="email" class="col-sm-4 control-label">邮箱：</label>
            <div class="col-sm-4">
              <input type="email" class="form-control" name="email" value="${email}" placeholder="Email">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-6 col-sm-2 text-right">
              <button type="submit" class="btn btn-default">确定</button>
            </div>
          </div>
        </form>

        <!-- 页面底端说明 -->
        <%@ include file="/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/pages/common/default_js.jsp"%>
</body>
</html>