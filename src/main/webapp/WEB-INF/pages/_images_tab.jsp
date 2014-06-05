<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:forEach items="${requestScope.images}" var="image" varStatus="status">
    <dl>
        <dd>
            <a rel="lightbox[roadtrip]" title="${image.descriptions}" href="<%= request.getContextPath() %>/images/show/${image.id}.html"> <img src="<%= request.getContextPath() %>/images/show/${image.id}.html" width="253" height="200" alt="${image.fileName}" title="${image.fileName}" /></a>
        </dd>
        <dt>
            <a title="${image.fileName}" href="<%= request.getContextPath() %>/images/show/${image.id}.html" rel="lightbox">图片${status.index+1}</a>&nbsp;&nbsp; <a href="javascript:void(0)" onclick="delete_image('<spring:message code="page.confirm.delete.images" />', '${image.id}')">删除</a>
        </dt>
    </dl>
</c:forEach>