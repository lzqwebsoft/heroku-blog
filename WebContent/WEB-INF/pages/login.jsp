<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博主登录-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>resources/style/login.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/default.js"></script>
</head>

<body>
<div id="modTopbar" class="mod-topbar">
  <div class="wrapper-box clearfix">
  
     <div class="left-box">&nbsp;</div>
     
     <div class="center-box">
         <ul class="q-menubox">
           <li class="q-menuitem">
              <a href="http://lzqwebsoft.herokuapp.com">首页</a>
           </li>
           <li class="q-menuitem">
              <a href="javascript:void(0)" onclick="show_about_dialog()">关于</a>
           </li>
         </ul>
     </div>
     
     <div class="right-box">
        <!--<ul class="q-navbox">
           <li class="q-navitem">
              <a href="javascript:void(0)"><img alt="管理" src="resource/images/set_icon.png"/></a>
              <!--子菜单
              <ul>
                <li>
                   <a href="javascript:void(0)" onclick="show_login_dialog();" >登&nbsp;&nbsp;&nbsp;录</a>
                </li>
                <li>
                   <a href="javascript:void(0)">发表博客</a>
                </li>
                <li>
                   <a href="javascript:void(0)">设&nbsp;&nbsp;&nbsp;置</a>
                </li>
             </ul>
           </li>
        </ul>-->
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
         <p id="error_info" style="display::none;">&nbsp;</p>
         <p><label for="username">帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label><input type="text" name="username" /></p>
         <p><label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label><input type="password" name="password" /></p>
         <p>
            <label for="validateCode">验证码：</label><input type="text" name="validateCode" style="width:100px;" />
         </p>
         <p><a href="javascript:void(0)">忘记密码</a></p>
         <p align="center"><input type="submit" value="登录" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="取消" /></p>
      </div>
      
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
    </div>
</div>
<div id="extraDiv1"><span></span></div>
</body>
</html>
