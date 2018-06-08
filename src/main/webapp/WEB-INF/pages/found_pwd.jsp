<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="zh-cn">">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>找回密码-<spring:message code="page.title" /></title>
    <%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div id="blog-header" class="container">
        <h2><spring:message code="page.label.foundPwd" /></h2>
        
        <p><spring:message code="page.label.foundPwd.sendMail.info" arguments="${email}"/></p>

        <!-- 页面底端说明 -->
        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
</body>
</html>