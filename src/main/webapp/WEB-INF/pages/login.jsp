<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博主登录-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/login.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/login.js"></script>
</head>

<body>
<div id="context-path" style="display:none"><%= request.getContextPath() %></div>
<div id="modTopbar" class="mod-topbar">
  <div class="wrapper-box clearfix">
  
     <div class="left-box">&nbsp;</div>
     
     <div class="center-box">
         <ul class="q-menubox">
           <li class="q-menuitem">
              <a href="<%= request.getContextPath() %>">首页</a>
           </li>
           <li class="q-menuitem">
              <a id="about-link" href="javascript:void(0)">关于</a>
           </li>
         </ul>
     </div>
     
     <div class="right-box">
     </div>
     
  </div>
  
</div>

<div class="goBackToTop">
   <a id="gototop" title="回到顶端" href="#container">TOP</a>
</div>

<div id="container">
    <div id="content-div">
      <h2>登录</h2>

      <div id="login_content">
         <form:form action="signIn.html" method="post" modelAttribute="user">
         <p id="error_info"><form:errors path="*" /></p>
         <table cellspacing="10" border="0">
            <tr>
               <td width="30%" align="right"><label for="username">帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label></td>
               <td align="left"><form:input path="userName" cssStyle="width:193px;"/></td>
               <td align="left">&nbsp;</td>
            </tr>
            <tr>
               <td align="right"><label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label></td>
               <td align="left"><form:password path="password" cssStyle="width:193px;" /></td>
               <td align="left">&nbsp;</td>
            </tr>
            <c:if test="${sessionScope.error_login_count!=null&&sessionScope.error_login_count>=3}">
            <tr style="white-space:nowrap;">
               <td align="right"><label for="validateCode">验证码：</label></td>
               <td align="left">
                   <input type="text" name="validateCode" style="width:90px; vertical-align:middle;" />
                   <img id="captcha-image" src="captcha.jpg" title="验证码" alt="验证码" style="vertical-align:middle;" />
               </td>
               <td align="left"><a id="update-captcha-link" href="javascript:void(0)">看不清,换一张</a></td>
             </tr>
             </c:if>
             <tr>
               <td align="right"><a href="javascript:void(0)">忘记密码&nbsp;&nbsp;&nbsp;</a></td>
               <td align="left">&nbsp;</td>
               <td align="left">&nbsp;</td>
             </tr>
             <tr>
               <td align="center" colspan="2">
                 <input type="submit" value="登录" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <input type="reset" value="重置" />
               </td>
               <td align="left">&nbsp;</td>
             </tr>
         </table>
         </form:form>
      </div>
      
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
    </div>
</div>
<div id="extraDiv1"><span></span></div>
</body>
</html>
