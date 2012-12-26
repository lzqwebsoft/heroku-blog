<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>/resources/javascript/lightbox/lightbox.js"></script>

<c:forEach items="${requestScope.images}" var="image" varStatus="idx">    
<dl>
   <dd>
      <a rel="lightbox[roadtrip]" title="${image.descriptions}" href="<%= request.getContextPath() %>/upload-images/${image.diskFilename}">
          <img src="<%= request.getContextPath() %>/upload-images/${image.diskFilename}" width="140" height="104" alt="${image.fileName}" title="${image.fileName}" />
      </a>
   </dd>
   <dt>
      <a title="${image.descriptions}" href="<%= request.getContextPath() %>/upload-images/${image.diskFilename}" rel="lightbox">图片${idx.index+1}</a>&nbsp;&nbsp;<a href="javascript:void(0)" >删除</a>
   </dt>
</dl>
</c:forEach>

<dl>
   <dd>
      <a rel="lightbox[roadtrip]" title="测试图片" href="<%= request.getContextPath() %>/resources/images/header.jpg">
          <img src="<%= request.getContextPath() %>/resources/images/header.jpg" width="140" height="104" alt="测试图片" title="" />
      </a>
   </dd>
   <dt>
      <a href="<%= request.getContextPath() %>/resources/images/header.jpg" rel="lightbox">测试图片</a>&nbsp;&nbsp;<a href="javascript:void(0)" >删除</a>
   </dt>
</dl>