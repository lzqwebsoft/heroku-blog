<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<h3>评论：</h3>
<c:forEach items="${comments}" var="comment">
<div class="root_comment">
   <p>
      <a href="<c:out value="${comment.website}" default="javascript:void(0)" />">${comment.reviewer}</a>发表于：<fmt:formatDate value="${comment.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/> 
      <span>
         <a href="#reply_article" title="回复" onclick="replay_comment('${comment.id}', '${comment.reviewer}')">回复</a>&nbsp;
         <c:if test="${sessionScope.user!=null}">
         <a href="javascript:void(0)" title="删除" onclick="delete_article_comment('${comment.id}')">删除</a>
         </c:if>
      </span>
   </p>
   <div class="comment_content"><c:out value="${comment.content}" escapeXml="false"/></div>
   <c:forEach items="${comment.childComments}" var="childComment">
   <div class="child_comment">
      <p>
          Re：<a href="<c:out value="${childComment.website}" default="javascript:void(0)" />">${childComment.reviewer}</a>发表于：<fmt:formatDate value="${childComment.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
          <span>
             <a href="#reply_article" title="回复" onclick="replay_comment('${comment.id}', '${childComment.reviewer}')">回复</a>&nbsp;
             <c:if test="${sessionScope.user!=null}">
             <a href="javascript:void(0)" title="删除" onclick="delete_article_comment('${childComment.id}')">删除</a>
             </c:if>
          </span>
      </p>
      <div class="comment_content"><c:out value="${childComment.content}" escapeXml="false"/></div>
   </div>
   </c:forEach>
</div>
</c:forEach>
<c:if test="${comments==null||fn:length(comments)<=0}">
<p class="no_comments">
  <fmt:message key="page.info.no.comments" />   
</p>
</c:if>