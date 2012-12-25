<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="author" content="Dave Shea" />
  <meta name="keywords" content="design, css," />
  <meta name="description" content="" />
  <meta name="robots" content="all" />
  <title><spring:message code="page.title" /></title>
  <style type="text/css" title="currentStyle" media="screen">
    @import "<%= request.getContextPath() %>/resources/style/comment.css";
  </style>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/index.js"></script>
  <!--<link rel="Shortcut Icon" type="image/x-icon" href="http://www.csszengarden.com/favicon.ico" />-->
</head>

<body id="css-zen-garden">
<%@ include file="/WEB-INF/pages/common/header.jsp" %>

<div id="container">
  <div id="intro">
    <div id="pageHeader">
      <c:if test="${requestScope.blogInfo!=null}">
      <h1><span>${requestScope.blogInfo.head}</span></h1>
      <h2><span>${requestScope.blogInfo.descriptions}</span></h2>
      </c:if>
    </div>

<!--    <div id="quickSummary">-->
<!--        <h2>In building...</h2>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>${sessionScope.user.userName }</p>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>&nbsp;</p>-->
<!--    </div>-->

<!--    <div id="preamble">-->
<!--      <h2>文章标题</h2>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>&nbsp;</p>-->
<!--          <p>&nbsp;</p>-->
<!--    </div>-->
  </div>

  <div id="supportingText">
    <c:if test="${articles!=null&&fn:length(articles)>0}">
    <c:forEach items="${articles}" var="article">
    <div class="explanation">
      <h3><a href="show/${article.id}.html"><c:out value="${article.title}" /></a></h3>
          <p class="article_date">发表于：<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
          <p class="article_description"><c:out value="${article.content}"  escapeXml="false"/></p>
          <p>
             <a href="show/${article.id}.html">阅读(100)</a>&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="show/${article.id}.html#reply_article">评论(0)</a>&nbsp;&nbsp;&nbsp;&nbsp;
             <c:if test="${sessionScope.user!=null}">
             <a href="edit/${article.id}.html">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="javascript:void(0)" onclick="confirm_article_delete('<spring:message code="page.confirm.delete.article" arguments="${article.title}"  />', '${article.id}')">删除</a>
             </c:if>
          </p>
    </div>
    </c:forEach>
    </c:if>

    <div class="explanation">
      <h3>文章标题</h3>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
    </div>

    <div class="explanation">
      <h3>文章标题</h3>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
    </div>

    <div class="explanation">
      <h3>文章标题</h3>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
    </div>
        
        <div class="explanation">
      <h3>文章标题</h3>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
    </div>
        
        <div id="page_number">
           <div id="page_description">共1页,4篇文章</div>
           <div id="page_count">
              <ul>
                <li><a href="javascript:void(0)">1</a></li>
                <li><a href="javascript:void(0)">2</a></li>
                <li><a href="javascript:void(0)">...</a></li>
                <li><a href="javascript:void(0)">下一页</a></li>
              </ul>
           </div>
        </div>

    <div id="footer">
       Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
    </div>

  </div>

  
  <div id="linkList">
    <div id="linkList2">
      <div id="lselect">
        <h3 class="select"><span>文章分类:</span></h3>
        <ul>
          <li><a href="javascript:void(0)">JSE(0)</a></li>
          <li><a href="javascript:void(0)">J2EE(0)</a></li>
          <li><a href="javascript:void(0)">Ruby(0)</a></li>
          <li><a href="javascript:void(0)">Ruby On Rails(0)</a></li>
          <li><a href="javascript:void(0)">Javascript/Ajax(0)</a></li>
          <li><a href="javascript:void(0)">网络转载</a></li>
        </ul>
      </div>

      <div id="larchives">
        <h3 class="archives"><span>阅读排行:</span></h3>
        <ul>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
          <li><a href="javascript:void(0)">XXXXXXXXXXXXX(0)</a></li>
        </ul>
      </div>
      
      <div id="lresources">
        <h3 class="resources"><span>网站链接:</span></h3>
        <ul>
          <li><a href="http://blog.csdn.net/xianqiang1">CSDN</a></li>
          <li><a href="http://my.oschina.net/websoft">OSChina</a></li>
          <li><a href="http://weibo.com/lzqwebsoft">Sina微博</a></li>
          <li><a href="https://twitter.com/lzqwebsoft">Twitter</a></li>
        </ul>
      </div>
            
      <div id="lconnect">
        <h3 class="connect"><span>联系我:</span></h3>
          <ul>
              <li>lzqwebsoft@gmail.com</li>
              <li>751939573@qq.com</li>
          </ul>
      </div>
    </div>
  </div>

</div>

<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>