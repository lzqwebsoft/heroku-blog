<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 导航菜单 -->
<div id="context-path" style="display:none"><%= request.getContextPath() %></div>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- 左导航菜单 -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value="/" />">飘痕の博客</a>
        </div>

        <!-- 右导航菜单 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="<%=request.getContextPath()%>/about.html" data-toggle="modal" data-target="#aboutDailog">关于</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <button type="button" class="btn btn-default navbar-btn dropdown-toggle" data-toggle="dropdown">管理<span class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <c:if test="${sessionScope.user==null}">
                            <li><a href="<%=request.getContextPath()%>/signIn.html">登&nbsp;&nbsp;录</a></li>
                        </c:if>
                        <c:if test="${sessionScope.user!=null}">
                            <li><a href="<%=request.getContextPath()%>/logout.html">注&nbsp;&nbsp;销</a></li>
                        </c:if>
                        <c:forEach items="${requestScope.menus}" var="menu"  varStatus="s">
                            <c:if test="${(s.count-1)%2==0}">
                                <li class="divider"></li>
                            </c:if>
                            <li><a href="<%= request.getContextPath() %>/${menu.path}">${menu.label}</a></li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- 回到页面顶点 -->
<div class="goBackToTop">
    <a id="gototop" href="#blog-header" title="回到顶端" style="display: none;">TOP</a>
</div>

<!-- 关于对话框主体 -->
<div class="modal fade" id="aboutDailog" tabindex="-1" role="dialog" aria-labelledby="aboutDailogLabel" aria-hidden="true"></div>

<!--[if lte IE 7]>
<div class="bs-header container alert alert-warning">
    <h1>骚年：</h1>
    <p>如果您在使用IE6、IE7浏览器以及也可能是带壳儿的国产浏览器，可能会看到本站丑陋的一面，对不住了；请主动升级您的浏览器，我不想为落后埋单！</p>
</div>
<![endif]-->