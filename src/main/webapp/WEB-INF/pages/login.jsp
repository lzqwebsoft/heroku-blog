<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>博主登录-<spring:message code="page.title" /></title>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
<style type="text/css">
.form-signin {
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
}

.form-signin .form-signin-heading {
    margin-bottom: 10px;
}

.form-signin .form-control {
    position: relative;
    font-size: 16px;
    height: auto;
    padding: 10px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin .input-group {
    margin: 5px 0;
}

.form-signin .form-control:focus {
    z-index: 2;
}

.form-signin>a {
    display: block;
    overflow: hidden;
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <!-- 主体内容 -->
    <div class="container">
        <h2>博主登录</h2>

        <div id="login_zone">
            <form:form action="signIn.html" method="post" modelAttribute="user" cssClass="form-signin">
                <c:set var="loginErrors"><form:errors path="*"/></c:set>
                <c:if test="${not empty loginErrors}">
                <div class="alert alert-danger">
                    <form:errors path="*"/>
                </div>
                </c:if>
                <div class="input-group">
                    <span class="input-group-addon"><div class='glyphicon glyphicon-user'></div></span><form:input path="userName" cssClass="form-control" placeholder="帐号"  required="true" autofocus="true"/>
                </div>
                <div class="input-group">
                    <span class="input-group-addon"><div class='glyphicon glyphicon-wrench'></div></span> <form:password path="password" cssClass="form-control" placeholder="密码" required="true" />
                </div>
                <c:if test="${sessionScope.error_login_count!=null&&sessionScope.error_login_count>=3}">
                    <div class="input-group">
                        <span class="input-group-addon"><div class='glyphicon glyphicon-picture'></div></span> <input type="text" name="validateCode" class="form-control" placeholder="验证码" required />
                    </div>
                    <span><img id="captcha-image" src="captcha.jpg" title="验证码" alt="验证码" /> <a href="javascript:void(0)" id="update-captcha-link">看不清，换一张</a></span>
                </c:if>
                <div class="input-group">
                    <a href="<c:url value="/forget_pwd.html" />">忘记密码</a>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">登 录</button>
            </form:form>
        </div>

        <!-- 页面底端说明 -->
        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
    <script type="text/javascript">
    $(function() {
        $("#update-captcha-link").click(function() {
            $("#captcha-image").attr("src", "captcha.jpg?"+ Math.floor(Math.random()*100));
        });
    });
    </script>
</body>
</html>
