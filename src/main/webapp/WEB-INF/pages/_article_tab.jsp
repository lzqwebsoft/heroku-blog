<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:choose>
<c:when test="${requestScope.articles!=null&&fn:length(requestScope.articles)>0}">
<table id="lstBox" cellspacing="0">
	<tr>
	  <th class="tdleft">标题</th>
	  <th style="width:50px;">阅读</th>
	  <th style="width:50px;">评论</th>
	  <th style="width:70px;">评论权限</th>
	  <th style="width:140px;">操作</th>
	</tr>
	<c:forEach items="${requestScope.articles}" var="article">
	<tr>
	   <td class='tdleft'>
	       <a href='<%= request.getContextPath() %>/show/${article.id}.html'><c:out value="${article.title}" /></a>
	       <span class='gray'>(<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span>
	   </td>
	   <td><c:out value="${article.readedNum}" /></td>
	   <td>0</td>
	   <td>
	      <c:choose>
	      <c:when test="${article.allowComment}">
	         <a href='javascript:void(0)' onclick="update_allow_comment('${article.id}', 'false', this);" class='lock'>禁止评论</a>
	      </c:when>
	      <c:otherwise>
	         <a href='javascript:void(0)' onclick="update_allow_comment('${article.id}', 'true', this);"  class='lock'>允许评论</a>
	      </c:otherwise>
	      </c:choose>
	   </td>
	   <td>
	       <a href='<%= request.getContextPath() %>/edit/${article.id}.html'>编辑</a> | 
	       <c:choose>
	       <c:when test="${article.isTop}">
	          <a href='javascript:void(0)' onclick="update_set_top('${article.id}', false, this)">取消置顶</a> | 
	       </c:when>
	       <c:otherwise>
	          <a href='javascript:void(0)' onclick="update_set_top('${article.id}', true, this)">置顶</a> | 
	       </c:otherwise>
	       </c:choose>
	       <a href='javascript:void(0)' onclick="confirm_article_delete('<spring:message code="page.confirm.delete.article" arguments="${article.title}"  />', '${article.id}')" name=del>删除</a>
	   </td>
	</tr>
	</c:forEach>
</table>
<div class="page_nav">
  <span> 5条数据 共1页</span>
  <strong>1</strong>
</div>
</c:when>

<c:otherwise>
   <p>暂无</p>
</c:otherwise>
</c:choose>