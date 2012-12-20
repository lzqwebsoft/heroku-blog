<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/article_type_tab.js" ></script>

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
       <span id="article_type_${articleType.id}"><a href='javascript:void(0)'><c:out value="${articleType.name}" /></a></span>
   </td>
   <td>0</td>
   <td>
       <a href='javascript:void(0)' onclick="edit_article_type('article_type_${articleType.id}'); return false">编辑</a> | 
       <a href='javascript:void(0)'>删除</a><br /> 
       <a href='javascript:void(0)' name=del>显示</a> | 
       <a href='javascript:void(0)' name=del>隐藏</a>
   </td>
</tr>
</c:forEach>
</c:if>
</table>

<div id="add_type_div">
   <input id="txtArticleType" class="t_input" type="text" maxlength="30" size="40" />
   <input id="btnAdd" class="t_btn" type="button" value="添加分类" />
   <c:if test="${requestScope.errorInfo!=null}">
      <label id="type-info"><c:out value="${requestScope.errorInfo}" /></label>
   </c:if>
</div>