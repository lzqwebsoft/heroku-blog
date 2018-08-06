<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:choose>
    <c:when test="${requestScope.images!=null && requestScope.images.data!=null && fn:length(requestScope.images.data)>0}">
        <c:forEach items="${requestScope.images.data}" var="image" varStatus="status">
            <dl>
                <dd>
                    <a rel="lightbox[roadtrip]" title="${image.descriptions}" href="<%= request.getContextPath() %>/images/show/${image.id}.html"> <img src="<%= request.getContextPath() %>/images/show/${image.id}.html" width="253" height="200" alt="${image.fileName}" title="${image.fileName}" /></a>
                </dd>
                <dt>
                    <a title="${image.fileName}" href="<%= request.getContextPath() %>/images/show/${image.id}.html" rel="lightbox">图片${status.index+1}</a>&nbsp;&nbsp; <a href="javascript:void(0)" onclick="delete_image('<spring:message code="page.confirm.delete.images" />', '${image.id}', '${requestScope.images.currentPageNo}')">删除</a>
                </dt>
            </dl>
        </c:forEach>

        <div class="image-pager-bar" style="clear: both;">
            <c:url value="/images/page.html" var="page_images_url" />
            <ul class="pagination">
                <c:if test="${requestScope.images.hasPreviousPage}">
                    <li><a href="javascript:void(0)" onclick="image_page_update('${page_images_url}', '${requestScope.images.currentPageNo-1}')">&laquo;</a></li>
                </c:if>
                <c:forEach var="index" begin="${requestScope.images.pageRangeStart}" end="${requestScope.images.pageRangeEnd}" step="1">
                    <c:choose>
                        <c:when test="${index!=requestScope.images.currentPageNo}">
                            <li><a href="javascript:void(0)" onclick="image_page_update('${page_images_url}', '${index}')">${index}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="active"><a href="javascript:void(0)">${index}<span class="sr-only">(current)</span></a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${requestScope.images.hasNextPage}">
                    <li><a href="javascript:void(0)" onclick="image_page_update('${page_images_url}', '${requestScope.images.currentPageNo+1}')">&raquo;</a></li>
                </c:if>
            </ul>
            <div id="page_description">${requestScope.images.totalCount}张图片,共${requestScope.images.totalPageCount}页</div>
        </div>
    </c:when>

    <c:otherwise>
        <p>暂无内容</p>
    </c:otherwise>
</c:choose>