<%@ page contentType="text/html; charset=UTF-8" %>
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
        <ul class="q-navbox">
           <li class="q-navitem">
              <a href="javascript:void(0)"><img alt="管理" src="<%= request.getContextPath() %>/resources/images/set_icon.png"/></a>
              <!--子菜单-->
              <ul>
                <li>
                   <a id="login-link" href="javascript:void(0)">登&nbsp;&nbsp;&nbsp;录</a>
                </li>
                <li>
                   <a href="<%= request.getContextPath() %>/new.html">发表博客</a>
                </li>
                <li>
                   <a href="<%= request.getContextPath() %>/change_password.html">修改密码</a>
                </li>
                <li>
                   <a href="<%= request.getContextPath() %>/set.html">设&nbsp;&nbsp;&nbsp;置</a>
                </li>
             </ul>
           </li>
        </ul>
     </div>
     
  </div>
</div>

<div class="goBackToTop">
   <a id="gototop" title="回到顶端" href="#container">TOP</a>
</div>