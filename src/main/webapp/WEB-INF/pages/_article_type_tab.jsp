<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<table id="lstBox" cellspacing="0">
<tr>
  <th class="tdleft">类别</th>
  <th style="width:120px;">文章</th>
  <th style="width:100px;">操作</th>
</tr>
<c:if test="${requestScope.articleTypes!=null&&fn:length(requestScope.articleTypes)>0}">
<c:forEach items="${requestScope.articleTypes}" var="articleType">
<tr>
   <td class='tdleft'>
       <span id="article_type_${articleType.id}"><c:out value="${articleType.name}" /></span>
   </td>
   <td>
      <c:out value="${fn:length(articleType.articles)}" />
   </td>
   <td>
       <a href='javascript:void(0)' onclick="edit_article_type('article_type_${articleType.id}', '${articleType.id}'); return false">编辑</a> | 
       <a href='javascript:void(0)' onclick="confirm_article_type_delete('<spring:message code="page.confirm.delete.articletype" arguments="${articleType.name}" />', '${articleType.id}');">删除</a><br /> 
       <c:if test="${articleType.disable}">
           <a href='javascript:void(0)' onclick="disable_article_type('${articleType.id}', false)" name=del>显示</a> | 隐藏
       </c:if>
       <c:if test="${!articleType.disable}">
                             显示 | <a href='javascript:void(0)' onclick="disable_article_type('${articleType.id}', true)" name=del>隐藏</a>
       </c:if> 
   </td>
</tr>
</c:forEach>
</c:if>
</table>

<div id="add_type_div">
   <input id="txtArticleType" class="t_input" type="text" maxlength="30" size="40" />
   <input id="btnAdd" class="t_btn" type="button" value="添加分类" onclick="add_article_type()" />
   <c:if test="${requestScope.errorInfo!=null}">
      <label id="type-info"><c:out value="${requestScope.errorInfo}" /></label>
   </c:if>
</div>