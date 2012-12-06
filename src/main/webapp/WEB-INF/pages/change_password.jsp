<%@ page contentType="text/html; charset=UTF-8" %>
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
         <p id="error_info" style="display::none;">&nbsp;</p>
         <table cellspacing="10" border="0">
         <tr>
            <td width="40%" align="right">
               <label for="password">旧密码：</label>
            </td>
            <td align="left"><input type="text" name="password" /></td>
         </tr>
         <tr>
           <td align="right">
               <label for="newPassword">新密码：</label>
           </td>
           <td align="left"><input type="password" name="newPassword" /></td>
         </tr>
         <tr>
           <td align="right">
              <label for="confirmPassword">确&nbsp;&nbsp;认：</label>
           </td>
           <td align="left"><input type="password" name="confirmPassword" /></td>
         </tr>
         <tr>
            <td align="center" colspan="2">
               <input type="submit" value="确定" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="取消" />
            </td>
         </tr>
         </table>
      </div>
      
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>
