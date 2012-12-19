<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/change_password.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/pages/common/header.jsp" />

<div id="container">
    <div id="content-div">
      <h2>修改密码</h2>
      
      <div id="login_content">
         <form:form action="changepwd_handle.html" method="post" modelAttribute="userBean">
         <p id="error_info" ><form:errors path="*" htmlEscape="false"/></p>
         <c:if test="${sessionScope.messages!=null}">
         <p id="message_info" ><c:out  value="${sessionScope.messages}"/></p>
         <c:remove  var="messages" scope="session"/>
         </c:if>
         <table width="100%" cellspacing="10" border="0">
         <tr>
            <td width="30%" align="right">
               <label for="password"><spring:message code="page.label.changepwd.password" />：</label>
            </td>
            <td align="left"><form:password path="password" /></td>
         </tr>
         <tr>
           <td align="right">
               <label for="newPassword"><spring:message code="page.label.changepwd.newpassword" />：</label>
           </td>
           <td align="left"><form:password path="newPassword" /></td>
         </tr>
         <tr>
           <td align="right">
              <label for="confirmPassword"><spring:message code="page.label.changepwd.confirmPassword" />：</label>
           </td>
           <td align="left"><form:password path="confirmPassword" /></td>
         </tr>
         <tr>
            <td align="center" colspan="2">
               <input type="submit" value="确定" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置" />
            </td>
         </tr>
         </table>
         </form:form>
      </div>
      
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>
