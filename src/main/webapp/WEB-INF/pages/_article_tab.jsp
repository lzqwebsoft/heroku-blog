<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:choose>
<c:when test="${requestScope.page!=null&&requestScope.page.data!=null&&fn:length(requestScope.page.data)>0}">
<table id="lstBox" cellspacing="0">
	<tr>
	  <th class="tdleft">标题</th>
	  <th style="width:50px;">阅读</th>
	  <th style="width:50px;">评论</th>
	  <th style="width:70px;">评论权限</th>
	  <th style="width:140px;">操作</th>
	</tr>
	<c:forEach items="${requestScope.page.data}" var="article">
	<tr>
	   <td class='tdleft'>
	       <a href='<%= request.getContextPath() %>/show/${article.id}.html'><c:out value="${article.title}" /></a>
	       <span class='gray'>(<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span>
	   </td>
	   <td><c:out value="${article.readedNum}" /></td>
	   <td><c:out value="${article.commentCount}" /></td>
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
	       <a href='javascript:void(0)' onclick="confirm_article_delete('<spring:message code="page.confirm.delete.article" arguments="${article.title}"  />', '${article.id}', '${requestScope.articleTypeId}', '${requestScope.title}', '${requestScope.page.currentPageNo}')" name=del>删除</a>
	   </td>
	</tr>
	</c:forEach>
</table>
<div id="page_number" style="margin-top: 10px; font: 14px/20px 'Microsoft YaHei',微软雅黑,Arial,Lucida Grande,Tahoma,sans-serif;">
  <c:url value="/article/select.html" var="page_url" />
  <c:if test="${requestScope.page!=null&&requestScope.page.data!=null&&fn:length(requestScope.page.data)>0}">
       <div id="page_description">${requestScope.page.totalCount}篇文章, 共${requestScope.page.totalPageCount}页</div>
       <div id="page_count">
          <ul>
            <c:if test="${requestScope.page.hasPreviousPage}">
               <li><a href="javascript:void(0)" onclick="article_page_update('${page_url}', '${requestScope.articleTypeId}', '${requestScope.title}', '${requestScope.page.currentPageNo-1}')">上一页</a></li>
            </c:if>
            
            <c:forEach var="index" begin="${requestScope.page.pageRangeStart}" end="${requestScope.page.pageRangeEnd}" step="1">
            <c:choose>
            <c:when test="${index!=requestScope.page.currentPageNo}">
               <li><a href="javascript:void(0)" onclick="article_page_update('${page_url}', '${requestScope.articleTypeId}', '${requestScope.title}', '${index}')">${index}</a></li>
            </c:when>
            <c:otherwise>
               <li><a href="javascript:void(0)" class="selected">${index}</a></li>
            </c:otherwise>
            </c:choose>
            </c:forEach>
            
            <c:if test="${requestScope.page.hasNextPage}">
               <li><a href="javascript:void(0)" onclick="article_page_update('${page_url}', '${requestScope.articleTypeId}', '${requestScope.title}', '${requestScope.page.currentPageNo+1}')">下一页</a></li>
            </c:if>
          </ul>
       </div>
 </c:if>
</div>
</c:when>

<c:otherwise>
   <p>暂无</p>
</c:otherwise>
</c:choose>