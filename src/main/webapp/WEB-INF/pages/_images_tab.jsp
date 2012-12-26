<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:forEach items="${requestScope.images}" var="image">    
<dl>
   <dd>
      <a rel="lightbox[roadtrip]" title="${image.descriptions}" href="<%= request.getContextPath() %>/upload-images/${image.diskFilename}">
          <img src="<%= request.getContextPath() %>/upload-images/${image.diskFilename}" width="140" height="104" alt="${image.fileName}" title="${image.fileName}" />
      </a>
   </dd>
   <dt>
      <a title="${image.descriptions}" href="<%= request.getContextPath() %>/upload-images/${image.diskFilename}" rel="lightbox">图片${image.id}</a>&nbsp;&nbsp;
      <a href="javascript:void(0)" onclick="delete_image('<spring:message code="page.confirm.delete.images" />', '${image.id}')" >删除</a>
   </dt>
</dl>
</c:forEach>