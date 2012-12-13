<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

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
        <ul class="q-navbox">
           <li class="q-navitem">
              <a href="javascript:void(0)"><img alt="管理" src="<%= request.getContextPath() %>/resources/images/set_icon.png"/></a>
              <!--子菜单-->
              <ul>
                 <c:if test="${sessionScope.user==null}">
                 <li>
                    <a id="login-link" href="javascript:void(0)">登&nbsp;&nbsp;&nbsp;录</a>
                 </li>
                 </c:if>
                 <c:if test="${sessionScope.user!=null}">
                 <li>
                    <a href="<%= request.getContextPath() %>/logout.html">注&nbsp;&nbsp;&nbsp;销</a>
                 </li>
                 </c:if>
                 <c:forEach items="${requestScope.menus}" var="menu">
                 <li>
                    <a href="<%= request.getContextPath() %>/${menu.path}">${menu.label}</a>
                 </li>
                 </c:forEach>
               </ul>
           </li>
        </ul>
     </div>
     
  </div>
</div>

<div class="goBackToTop">
   <a id="gototop" title="回到顶端" href="#container">TOP</a>
</div>