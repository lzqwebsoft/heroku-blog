<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:choose>
<c:when test="${requestScope.drafts!=null&&fn:length(requestScope.drafts)>0}">
<table id="lstBox" cellspacing="0">
<tr>
  <th class="tdleft">标题</th>
  <th style="width:50px;">阅读</th>
  <th style="width:50px;">评论</th>
  <th style="width:70px;">评论权限</th>
  <th style="width:140px;">操作</th>
</tr>
<c:forEach items="${requestScope.drafts}" var="draft">
<tr>
   <td class='tdleft'>
       <a href='<%= request.getContextPath() %>/show/${draft.id}.html'><c:out value="${draft.title}" /></a>
       <span class='gray'>(<fmt:formatDate value="${draft.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span>
   </td>
   <td><c:out value="${draft.readedNum}" /></td>
   <td>0</td>
   <td><a href='javascript:void(0)' class='lock'>&nbsp;</a></td>
   <td>
       <a href='<%= request.getContextPath() %>/edit/${draft.id}.html'>编辑</a> | 
       <a href='javascript:void(0)' onclick="confirm_draft_delete('<spring:message code="page.confirm.delete.article" arguments="${draft.title}"  />', '${draft.id}')" name=del>删除</a>
   </td>
</tr>
</c:forEach>
</table>
<div class="page_nav">
  <span> 1条数据 共1页</span>
  <strong>1</strong>
</div>
</c:when>

<c:otherwise>
   <p>暂无</p>
</c:otherwise>
</c:choose>