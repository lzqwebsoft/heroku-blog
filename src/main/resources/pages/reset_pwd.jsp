<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover">
<title>重置密码-<spring:message code="page.title" /></title>
<%@ include file="/pages/common/default_css.jsp"%>
</head>

<body>
    <%@ include file="/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div id="blog-header" class="container">
        <h2>重置密码</h2>
        <form action="<c:url value="/reset_pwd.html" />" method="post" class="form-horizontal" role="form">
            <p class="text-center" style="color:red;">
                <c:if test="${errorInfos!=null&&fn:length(errorInfos)>0}">
                    <c:forEach items="${errorInfos}" var="errorInfo">
                        <c:out value="${errorInfo}" />
                        <br />
                    </c:forEach>
                </c:if>
            </p>
            <div class="form-group">
                <label for="newPassword" class="col-sm-4 control-label"><spring:message code="page.label.changepwd.newpassword" />：</label>
                <div class="col-sm-4">
                    <input type="password" name="newPassword" value="${newPassword}" class="form-control" required autofocus />
                    <input type="hidden" name="sid" value="${sid}" /> <input type="hidden" name="uid" value="${uid}" />
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="col-sm-4 control-label"><spring:message code="page.label.changepwd.confirmPassword" />：</label>
                <div class="col-sm-4">
                    <input type="password" name="confirmPassword" maxlength="20" value="${confirmPassword}" class="form-control" required />
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-6 col-sm-2 text-right">
                    <button type="submit" class="btn btn-default">确定</button>
                </div>
            </div>
        </form>

        <%@ include file="/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/pages/common/default_js.jsp"%>
</body>
</html>