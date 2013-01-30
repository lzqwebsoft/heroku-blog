<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="page.title" /></title>
    <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
    <style type="text/css">
       div form p {
          margin: 7px;
       }
       div form p input[type=submit] {
          padding: 3px 5px 1px 5px;
          font-size: 80%;
          
       }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp" %>
    
    <div id="container">
        <div id="content-div" style="margin: 50px 40px 10px 40px;">
            <h2><spring:message code="page.label.foundPwd" /></h2>
            <p style="margin: 15px;"><spring:message code="page.label.foundPwd.prompt.info" /></p>
            <div style="margin: 10px auto; width: 300px; height: 130px;">
               <form action="<c:url value="/found_pwd.html" />" method="post">
               <p style="color: red;">${errorInfo}</p>
               <p><label for="email">邮箱：</label><input type="text" name="email" value="${email}" style="padding: 2px; width:193px;" /></p>
               <p style="text-align: right; padding-right: 38px;"><input type="submit" value="确定" /></p>
               </form>
            </div>
        </div>
        
        <div style="text-align: center; font: 14px/20px 'Microsoft YaHei',微软雅黑,Arial,Lucida Grande,Tahoma,sans-serif;">
          Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
        </div>
    </div>
    
    <%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>